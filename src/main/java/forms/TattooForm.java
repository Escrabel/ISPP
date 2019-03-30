
package forms;

import java.io.IOException;
import java.util.Collection;

import javax.validation.Valid;

import org.apache.commons.io.FilenameUtils;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.web.multipart.MultipartFile;

import domain.Tag;
import domain.Tattoo;

public class TattooForm {

	@Valid
	Tattoo			tattoo;
	MultipartFile	archivo;
	Collection<Tag>	tags;
	String			extension;


	public TattooForm() {
		super();
	}

	public TattooForm(final Tattoo tattoo) {
		super();
		this.tattoo = tattoo;
	}

	public Tattoo getTattoo() {
		return this.tattoo;
	}

	public void setTattoo(final Tattoo tattoo) {
		this.tattoo = tattoo;
	}

	public Collection<Tag> getTags() {
		return this.tags;
	}

	public void setTags(final Collection<Tag> tags) {
		this.tags = tags;
	}

	public MultipartFile getArchivo() {
		return this.archivo;
	}

	public void setArchivo(final MultipartFile archivo) {
		this.archivo = archivo;
	}

	public String getExtension() {
		return this.extension;
	}

	public void setExtension(final String extension) {
		this.extension = extension;
	}

	public byte[] getArchivoBase64() {
		//Cojo la extension del archivo y se la anyado al atributo de nuestro form
		final String extension = FilenameUtils.getExtension(this.getArchivo().getOriginalFilename());
		this.setExtension(extension);
		final String cabeceraString = "data:image/" + extension + ";base64,";
		//Juntamos la cabecera con el contenido en un byte[] unico
		final byte[] result = this.unirCabeceraConFichero(cabeceraString.getBytes());
		return result;
	}

	private byte[] unirCabeceraConFichero(final byte[] cabecera) {
		try {
			final byte[] fichero = Base64.encode(this.getArchivo().getBytes());
			final byte[] result = new byte[cabecera.length + fichero.length];
			System.arraycopy(cabecera, 0, result, 0, cabecera.length);
			System.arraycopy(fichero, 0, result, cabecera.length, fichero.length);
			return result;
		} catch (final IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
