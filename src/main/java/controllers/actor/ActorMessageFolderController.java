
package controllers.actor;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.MessageFolderService;
import services.MessageService;
import controllers.AbstractController;
import domain.Message;
import domain.MessageFolder;

@Controller
@RequestMapping("/messageFolder/actor")
public class ActorMessageFolderController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private MessageFolderService	folderService;

	@Autowired
	private LoginService			loginService;

	@Autowired
	private MessageService			messageService;


	// Constructors -----------------------------------------------------------

	public ActorMessageFolderController() {
		super();
	}

	//List Rest
	@RequestMapping(value = "/listRest", method = RequestMethod.GET)
	public @ResponseBody
	Collection<MessageFolder> listRest() {
		Collection<MessageFolder> result;
		result = this.loginService.getPrincipalActor().getMessageFolders();
		return result;
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<MessageFolder> folders;

		folders = this.loginService.getPrincipalActor().getMessageFolders();
		result = new ModelAndView("actor/messageFolder/list");
		result.addObject("folders", folders);
		return result;
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		MessageFolder f;
		f = this.folderService.create();
		f.setActor(this.loginService.getPrincipalActor());
		f.setIsModificable(true);
		result = this.createEditModelAndView(f);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int folderId) {
		ModelAndView result;
		MessageFolder folder;
		folder = this.folderService.findOne(folderId);
		Assert.notNull(folder);
		Assert.isTrue(this.loginService.getPrincipalActor().getId() == folder.getActor().getId());
		Assert.isTrue(folder.getIsModificable());
		result = this.createEditModelAndView(folder);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final MessageFolder folder, final BindingResult binding) {
		ModelAndView result;
		if (folder.getId() != 0) {
			final MessageFolder fbd = this.folderService.findOne(folder.getId());
			Assert.notNull(fbd);
			Assert.isTrue(this.loginService.getPrincipalActor().getId() == fbd.getActor().getId());
			Assert.isTrue(fbd.getIsModificable());
			folder.setMessages(fbd.getMessages());
		} else {
			Assert.isTrue(this.loginService.getPrincipalActor().getId() == folder.getActor().getId());
			Assert.isTrue(folder.getIsModificable());
		}

		if (binding.hasErrors())
			result = this.createEditModelAndView(folder);
		else if (this.folderService.isReservedName(folder))
			result = this.createEditModelAndView(folder, "folder.commit.error.name");
		else if (this.folderService.existsMessageFolder(folder))
			result = this.createEditModelAndView(folder, "folder.commit.error.name.exist");
		else
			try {
				this.folderService.save(folder);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(folder, "folder.commit.error");
			}

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final MessageFolder folder, final BindingResult binding) {
		ModelAndView result;
		if (folder.getId() != 0) {
			final MessageFolder fbd = this.folderService.findOne(folder.getId());
			Assert.notNull(fbd);
			Assert.isTrue(this.loginService.getPrincipalActor().getId() == fbd.getActor().getId());
			Assert.isTrue(fbd.getIsModificable());
		}
		Assert.isTrue(this.loginService.getPrincipalActor().getId() == folder.getActor().getId());
		Assert.isTrue(folder.getIsModificable());
		try {
			for (final Message m : folder.getMessages()) {
				final MessageFolder f = this.folderService.getPapelera(this.loginService.getPrincipalActor());
				m.setMessageFolder(f);
				final List<Message> lm = f.getMessages();
				lm.add(m);
				f.setMessages(lm);
				this.folderService.save(f);
				this.messageService.save(m);
			}
			this.folderService.delete(folder);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(folder, "folder.commit.error");
			System.out.println(oops.getMessage());
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final MessageFolder folder) {
		ModelAndView result;

		result = this.createEditModelAndView(folder, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final MessageFolder folder, final String message) {
		ModelAndView result;

		result = new ModelAndView("actor/messageFolder/edit");
		result.addObject("messageFolder", folder);
		result.addObject("message", message);

		return result;
	}

}
