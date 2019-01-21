package de.edirom.meigarage;

import de.edirom.meigarage.xml.RNGValidator;
import de.edirom.meigarage.xml.XmlValidator;
import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;
import pl.psnc.dl.ege.types.DataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Singleton - prepares available XML validators.<br/>
 * Basic configuration of every XML validator is parsed from 'mei-validators.xml'
 * file - provided with this implementation.
 * 
 * @author mariuszs
 */
public class XmlValidatorsProvider extends DefaultHandler {

	private static final Logger logger = Logger
			.getLogger(XmlValidatorsProvider.class);

	public static final String T_VALIDATOR = "validator";
	public static final String A_FORMAT = "format";
	public static final String A_MIMETYPE = "mimeType";
	public static final String A_SCHEMA = "rng";
	/*
	 * One XML validator for data type.
	 */
	private final Map<DataType, XmlValidator> xmlValidators = new HashMap<DataType, XmlValidator>();


	private XmlValidatorsProvider() {
		try {
			XMLReader xmlReader = XMLReaderFactory.createXMLReader();
			xmlReader.setContentHandler(this);
			xmlReader.parse(new InputSource(this.getClass()
					.getResourceAsStream("/mei-validators.xml")));
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
	}

	/*
	 * Thread safe singleton.
	 */
	private static class XmlValidatorsProviderHolder {
		private static final XmlValidatorsProvider INSTANCE = new XmlValidatorsProvider();
	}

	/**
	 * Provider as singleton - method returns instance.
	 * 
	 * @return
	 */
	public static XmlValidatorsProvider getInstance() {
		return XmlValidatorsProviderHolder.INSTANCE;
	}

	/* TODO : properly handled errors - empty xml attributes etc. */
	@Override
	public void startElement(String uri, String localName, String name,
			Attributes attributes) throws SAXException {

		try {

			if (localName.equals(T_VALIDATOR)) {
				DataType cDataType = new DataType(attributes.getValue(A_FORMAT),
						attributes.getValue(A_MIMETYPE));

				XmlValidator validator = new RNGValidator(attributes.getValue(A_SCHEMA));

				xmlValidators.put(cDataType, validator);
			}
		} catch (Exception ex) {
			throw new SAXException("Configuration errors occured.");
		}
	}

	/*
	 * Retrieves default URLs of aliases from .jar file 'suffix' marks relative
	 * path in .jar to gramma file.
	 */
	private String generateAliasURL(String suffix) {
		StringBuffer sb = new StringBuffer();
		sb.append("jar:file:");
		sb.append(getClass().getProtectionDomain().getCodeSource()
				.getLocation().getFile());
		sb.append(suffix);
		return sb.toString();
	}

	/**
	 * Returns XML validator by selected data type (null if data type is not
	 * supported).
	 * 
	 * @param dataType
	 * @return
	 */
	public XmlValidator getValidator(DataType dataType) {
		return xmlValidators.get(dataType);
	}

	/**
	 * Returns supported data types.
	 * 
	 * @return
	 */
	public List<DataType> getSupportedDataTypes() {
		return new ArrayList<DataType>(xmlValidators.keySet());
	}

}
