
package utilities;

import javax.sql.DataSource;

public interface TattooDAO {

	/**
	 * This is the method to be used to initialize
	 * database resources ie. connection.
	 */
	public void setDataSource(DataSource ds);

	/**
	 * This is the method to be used to update
	 * a record into the Tattoo table.
	 */
	public void updateImage(Integer id, byte[] imageData);

	public void updateImage(String name, byte[] imageData);

	public void updateImage(String name, String base64Img);
}
