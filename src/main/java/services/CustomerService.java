
package services;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.itextpdf.text.DocumentException;

import repositories.CustomerRepository;
import security.Authority;
import utilities.PdfUtilities;
import domain.Customer;
import domain.Message;
import domain.MessageFolder;

@Service
@Transactional
public class CustomerService {

	// Managed repository
	// -------------------------------------------------------------------------

	@Autowired
	private CustomerRepository customerRepository;

	// Suporting service
	// --------------------------------------------------------------------------

	@Autowired
	private MessageFolderService folderService;

	@Autowired
	private ActorService actorService;
	@Autowired
	private MessageService			messageService;
	

	// Simple crud methods
	// ------------------------------------------------------------------------

	public Customer create() {
		final Customer customer = new Customer();
		// customer = (Customer) this.folderService.addMessageFolders(customer);
		this.actorService.newActorDefault(customer, Authority.CUSTOMER);
		return customer;
	}

	public Collection<Customer> findAll() {

		final Collection<Customer> customers = this.customerRepository.findAll();
		Assert.notNull(customers);

		return customers;
	}

	public Customer findOne(final int customerId) {

		Assert.notNull(customerId);

		final Customer customer = this.customerRepository.findOne(customerId);

		Assert.notNull(customer);
		return customer;
	}

	public Customer save(Customer customer) {

		Assert.notNull(customer);
		this.actorService.setHashPassword(customer);
		if (customer.getId() == 0)
			customer = (Customer) this.folderService.addMessageFolders(customer);
		final Customer csave = this.customerRepository.save(customer);
		Assert.notNull(csave);
		Assert.isTrue(this.customerRepository.exists(csave.getId()));
		return csave;
	}
	
	public void delete(final Customer customer) {
		Assert.notNull(customer);
		Assert.notNull(this.customerRepository.findOne(customer.getId()));

		Collection<Message> messages = this.messageService.findAllByPrincipal();
		for (Message ms : messages) {
			Collection<MessageFolder> folders = this.folderService.getMessageFoldersByUser();

			ms.getMessageFolder().setName("papelera");

			this.messageService.delete(ms);

			for (MessageFolder b : folders) {
				b.setMessages(new ArrayList<Message>());
				this.folderService.saveDirect(b);
			}

		}

		Collection<MessageFolder> boxs = this.folderService.findAllByActorPrincipal();
		for (MessageFolder bs : boxs) {
			bs.setIsModificable(true);
			this.folderService.delete(bs);

		}


		this.customerRepository.delete(customer);

		Assert.isNull(this.customerRepository.findOne(customer.getId()));
	}


	// Other business methods
	// ---------------------------------------------------------------------

	public Customer findByPrincipal() {

		final Customer customer = this.actorService.findPrincipal();

		return customer;
	}

	public ByteArrayOutputStream generatePdf() throws DocumentException {
		ByteArrayOutputStream retorno = new ByteArrayOutputStream();
		final Customer c = this.findByPrincipal();

		retorno = PdfUtilities.generatePdfCustomer(c);

		return retorno;
	}

}
