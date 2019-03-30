
package utilities;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import domain.Tattoo;

public class TattooMapper implements RowMapper<Tattoo> {

	@Override
	public Tattoo mapRow(final ResultSet rs, final int rowNum) throws SQLException {
		final Tattoo Tattoo = new Tattoo();
		Tattoo.setId(rs.getInt("id"));
		Tattoo.setName(rs.getString("name"));
		Tattoo.setVersion(rs.getInt("version"));
		Tattoo.setImg(rs.getBytes("img"));
		Tattoo.setTicker(rs.getString("ticker"));
		Tattoo.setDescription(rs.getString("description"));
		Tattoo.setPrice(rs.getDouble("price"));
		Tattoo.setDateUpload(rs.getDate("dateUpload"));
		//		Tattoo.setImg(rs.getBytes("tattooist"));
		//		Tattoo.setImg(rs.getBytes("tags"));
		return Tattoo;
	}
}
