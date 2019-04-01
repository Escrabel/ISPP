/*
 * PopulateDatabase.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package utilities;

// import org.apache.commons.codec.binary.Base64
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import utilities.internal.DatabaseUtil;
import utilities.internal.EclipseConsole;
import utilities.internal.SchemaPrinter;
import utilities.internal.ThrowablePrinter;
import domain.DomainEntity;

public class PopulateDatabase {

	public static void main(final String[] args) {
		DatabaseUtil databaseUtil;
		ApplicationContext populationContext = null;
		Map<String, DomainEntity> entityMap;
		List<Entry<String, DomainEntity>> sortedEntities;

		EclipseConsole.fix();
		LogManager.getLogger("org.hibernate").setLevel(Level.OFF);
		databaseUtil = null;

		try {
			System.out.println("PopulateDatabase 1.12");
			System.out.println("---------------------");
			System.out.println();

			System.out.printf("Initialising persistence context `%s'.%n", DatabaseConfig.PersistenceUnit);
			databaseUtil = new DatabaseUtil();
			databaseUtil.initialise();

			System.out.printf("Creating database `%s' (%s).%n", databaseUtil.getDatabaseName(), databaseUtil.getDatabaseDialectName());
			databaseUtil.recreateDatabase();

			System.out.printf("Reading web of entities from `%s'.", DatabaseConfig.entitySpecificationFilename);
			populationContext = new ClassPathXmlApplicationContext("classpath:PopulateDatabase.xml");
			entityMap = populationContext.getBeansOfType(DomainEntity.class);
			System.out.printf(" (%d entities found).%n", entityMap.size());

			System.out.printf("Computing a topological order for your entities.%n");
			sortedEntities = PopulateDatabase.sort(databaseUtil, entityMap);

			System.out.printf("Trying to save the best order found.  What out for exceptions!%n");
			PopulateDatabase.persist(databaseUtil, sortedEntities);

			System.out.printf("Saving entity map to `%s'.%n", DatabaseConfig.entityMapFilename);
			PopulateDatabase.saveEntityMap(databaseUtil, sortedEntities);
		} catch (final Throwable oops) {
			ThrowablePrinter.print(oops);
		} finally {
			if (databaseUtil != null) {
				System.out.printf("Shutting persistence context `%s' down.%n", DatabaseConfig.PersistenceUnit);
				databaseUtil.shutdown();
			}
		}
		final TattooJDBCTemplate TattooJDBCTemplate = (TattooJDBCTemplate) populationContext.getBean("TattooJDBCTemplate");
	}

	private static String getBase64Image(final String path) throws IOException {
		//		final byte[] encoded = Files.readAllBytes(Paths.get("tattooImages//" + path));
		final byte[] encoded = Files.readAllBytes(Paths.get("src/main/java/utilities/tattooImages/" + path));
		return new String(encoded, Charset.defaultCharset());
		//		final File file = new File(".");
		//		for (final String fileNames : file.list())
		//			System.out.println(fileNames);
		//		final Scanner scanner = new Scanner(new File("src/main/java/utilities/tattooImages/01.txt"));
		//		final String text = scanner.useDelimiter("\\A").next();
		//		scanner.close();

		//		return text;
	}
	//	public static String encodeToString(final BufferedImage image) {
	//		String imageString = null;
	//		final String type = "png";
	//		final ByteArrayOutputStream bos = new ByteArrayOutputStream();
	//
	//		try {
	//			ImageIO.write(image, type, bos);
	//			final byte[] imageBytes = bos.toByteArray();
	//			
	//			final BASE64Encoder encoder = new BASE64Encoder();
	//			imageString = Base64.encode(imageBytes);
	//
	//			bos.close();
	//		} catch (final IOException e) {
	//			e.printStackTrace();
	//		}
	//		return imageString;
	//	}
	//
	//	private static byte[] getEncodedImage(final String url) {
	//		BufferedImage image = null;
	//		try {
	//			image = ImageIO.read(PopulateDatabase.class.getResourceAsStream("tattooImages/" + url));
	//		} catch (final IOException e) {
	//			e.printStackTrace();
	//		}
	//		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	//		try {
	//			ImageIO.write(image, "png", outputStream);
	//		} catch (final IOException e) {
	//			e.printStackTrace();
	//		}
	//		return Base64.encode(outputStream.toByteArray());
	//	}
	protected static List<Entry<String, DomainEntity>> sort(final DatabaseUtil databaseUtil, final Map<String, DomainEntity> entityMap) {
		LinkedList<Entry<String, DomainEntity>> result;
		LinkedList<Entry<String, DomainEntity>> cache;
		Entry<String, DomainEntity> entry;
		DomainEntity entity;
		int passCounter;
		boolean done;

		result = new LinkedList<Entry<String, DomainEntity>>();
		result.addAll(entityMap.entrySet());
		cache = new LinkedList<Entry<String, DomainEntity>>();
		passCounter = 0;

		do {
			try {
				databaseUtil.startTransaction();
				PopulateDatabase.cleanEntities(result);

				while (!result.isEmpty()) {
					entry = result.getFirst();
					entity = entry.getValue();
					databaseUtil.persist(entity);
					result.removeFirst();
					cache.addLast(entry);
				}
				databaseUtil.rollbackTransaction();
				done = true;
				result.addAll(cache);
				cache.clear();
			} catch (final Throwable oops) {
				databaseUtil.rollbackTransaction();
				done = (passCounter >= entityMap.size() - 1);
				entry = result.removeFirst();
				cache.addAll(result);
				cache.addLast(entry);
				result.clear();
				result.addAll(cache);
				cache.clear();
			}
			passCounter++;
		} while (!done);

		PopulateDatabase.cleanEntities(result);

		return result;
	}

	protected static void persist(final DatabaseUtil databaseUtil, final List<Entry<String, DomainEntity>> sortedEntities) {
		String name;
		DomainEntity entity;

		System.out.println();
		databaseUtil.startTransaction();
		for (final Entry<String, DomainEntity> entry : sortedEntities) {
			name = entry.getKey();
			entity = entry.getValue();

			System.out.printf("> %s = ", name);
			databaseUtil.persist(entity);
			SchemaPrinter.print(entity);
		}
		databaseUtil.commitTransaction();
		System.out.println();
	}

	private static void saveEntityMap(final DatabaseUtil databaseUtil, final List<Entry<String, DomainEntity>> sortedEntities) {
		Properties map;

		map = new Properties();
		for (final Entry<String, DomainEntity> entry : sortedEntities) {
			String key, value;

			key = entry.getKey();
			value = Integer.toString(entry.getValue().getId());

			map.put(key, value);
		}

		try (OutputStream stream = new FileOutputStream(DatabaseConfig.entityMapFilename)) {
			map.store(stream, DatabaseConfig.entityMapFilename);
		} catch (final Throwable oops) {
			throw new RuntimeException(oops);
		}
	}

	protected static void cleanEntities(final LinkedList<Entry<String, DomainEntity>> result) {
		for (final Entry<String, DomainEntity> entry : result) {
			DomainEntity entity;

			entity = entry.getValue();
			entity.setId(0);
			entity.setVersion(0);
		}
	}

	public static byte[] hexStringToByteArray(final String s) {
		final int len = s.length();
		final byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2)
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
		return data;
	}

}
