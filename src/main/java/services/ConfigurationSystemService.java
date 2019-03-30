
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ConfigurationSystemRepository;
import domain.ConfigurationSystem;

@Service
@Transactional
public class ConfigurationSystemService {

	// Manage Repository

	@Autowired
	private ConfigurationSystemRepository configurationSystemRepository;

	// Supporting services

	// Simple CRUD methods

	public ConfigurationSystem create() {

		final ConfigurationSystem configurationSystem = new ConfigurationSystem();

		return configurationSystem;
	}

	public void delete(final ConfigurationSystem s) {

		Assert.notNull(s);
		Assert.notNull(this.configurationSystemRepository.findOne(s.getId()));

		this.configurationSystemRepository.delete(s);

		Assert.isNull(this.configurationSystemRepository.findOne(s.getId()));
	}

	public ConfigurationSystem save(final ConfigurationSystem s) {

		Assert.notNull(s);

		final ConfigurationSystem sn = this.configurationSystemRepository.save(s);

		Assert.notNull(sn);
		Assert.isTrue(this.configurationSystemRepository.exists(sn.getId()));

		return sn;

	}

	public Collection<ConfigurationSystem> findAll() {

		final Collection<ConfigurationSystem> configurationSystem = this.configurationSystemRepository.findAll();
		Assert.notNull(configurationSystem);

		return configurationSystem;
	}

	public ConfigurationSystem findOne(final Integer id) {

		Assert.isTrue(id > 0);

		final ConfigurationSystem configurationSystem = this.configurationSystemRepository.findOne(id);
		Assert.notNull(configurationSystem);

		return configurationSystem;
	}

	// Other business methods

	public Collection<String> getDefaultTags() {
		Collection<String> res = this.configurationSystemRepository.getDefaultTags();
		Assert.notNull(res);
		return res;
	}


	public Collection<String> getCodeDiscounts() {
		Collection<String> res = this.configurationSystemRepository.getCodeDiscounts();
		Assert.notNull(res);
		return res;
	}
	public Double getDiscounts() {
		Double res = this.configurationSystemRepository.getDiscounts();
		Assert.notNull(res);
		return res;
	}

	public Double getComission() {
		Double res = this.configurationSystemRepository.getComission();
		Assert.notNull(res);
		return res;
	}

	public ConfigurationSystem findDefaultConfiguration() {
		return this.configurationSystemRepository.findDefaultConfiguration();

	}

	public void deleteCodeDiscount(String code) {

		ConfigurationSystem configurationSystem = this.findDefaultConfiguration();

		Collection<String> codes;

		if (code != null && configurationSystem.getCodeDiscounts().contains(code)) {

			codes = configurationSystem.getCodeDiscounts();

			codes.remove(code);

			configurationSystem.setCodeDiscounts(codes);

		}

		this.save(configurationSystem);

	}

}
