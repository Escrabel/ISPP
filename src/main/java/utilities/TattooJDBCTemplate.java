
package utilities;

import java.io.ByteArrayInputStream;
import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.lob.DefaultLobHandler;

public class TattooJDBCTemplate implements TattooDAO {

	private DataSource		dataSource;
	private JdbcTemplate	jdbcTemplateObject;


	@Override
	public void setDataSource(final DataSource dataSource) {
		this.dataSource = dataSource;
	}
	@Override
	public void updateImage(final String name, final String base64Img) {
		final MapSqlParameterSource in = new MapSqlParameterSource();
		in.addValue("name", name);
		in.addValue("img", "data:image/png;base64," + base64Img);

		final String SQL = "update `Acme-Tattoo`.Tattoo set BLOB_COLUMN = :img where name = :name";
		final NamedParameterJdbcTemplate jdbcTemplateObject = new NamedParameterJdbcTemplate(this.dataSource);

		jdbcTemplateObject.update(SQL, in);
		System.out.println("Updated Record with NAME = " + name);
	}
	@Override
	public void updateImage(final Integer id, final byte[] imageData) {
		final MapSqlParameterSource in = new MapSqlParameterSource();
		in.addValue("id", id);
		in.addValue("img", new SqlLobValue(new ByteArrayInputStream(imageData), imageData.length, new DefaultLobHandler()), Types.BLOB);

		final String SQL = "update `Acme-Tattoo`.Tattoo set BLOB_COLUMN = :img where id = :id";
		final NamedParameterJdbcTemplate jdbcTemplateObject = new NamedParameterJdbcTemplate(this.dataSource);

		jdbcTemplateObject.update(SQL, in);
		System.out.println("Updated Record with ID = " + id);

	}
	@Override
	public void updateImage(final String name, final byte[] imageData) {
		final MapSqlParameterSource in = new MapSqlParameterSource();
		in.addValue("name", name);
		in.addValue("img", new SqlLobValue(new ByteArrayInputStream(imageData), imageData.length, new DefaultLobHandler()), Types.BLOB);

		final String SQL = "update `Acme-Tattoo`.Tattoo set BLOB_COLUMN = :img where name = :name";
		final NamedParameterJdbcTemplate jdbcTemplateObject = new NamedParameterJdbcTemplate(this.dataSource);

		jdbcTemplateObject.update(SQL, in);
		System.out.println("Updated Record with NAME = " + name);
	}
}
