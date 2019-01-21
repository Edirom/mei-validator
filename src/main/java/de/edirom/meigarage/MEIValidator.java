package de.edirom.meigarage;

import org.apache.log4j.Logger;
import pl.psnc.dl.ege.component.Validator;
import pl.psnc.dl.ege.exception.EGEException;
import pl.psnc.dl.ege.exception.ValidatorException;
import pl.psnc.dl.ege.types.DataType;
import pl.psnc.dl.ege.types.ValidationResult;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


/**
 * <p>MEI implementation of Validator interface.</p>
 * Provides validation of MEI XML data.
 *
 * @author roewenstrunk
 *
 */
public class MEIValidator implements Validator {

    private static final Logger LOGGER = Logger.getLogger(MEIValidator.class);

    private static final XmlValidatorsProvider provider = XmlValidatorsProvider
            .getInstance();


    public List<DataType> getSupportedValidationTypes()
    {
        return provider.getSupportedDataTypes();
    }


    public ValidationResult validate(InputStream inputData,
                                     DataType inputDataType)
            throws IOException, ValidatorException, EGEException
    {
        checkIfSupported(inputDataType);
        /*
        XmlValidator validator = (XmlValidator)provider.getValidator(inputDataType);
        try {
            StandardErrorHandler seh = new StandardErrorHandler();
            validator.validateXml(inputData,seh);
            return seh.getValidationResult();
        }
        catch (SAXParseException ex) {
            return new ValidationResult(ValidationResult.Status.FATAL,
                    "Error in line (" + ex.getLineNumber() + "), column  ("
                            + ex.getColumnNumber() + ") : " + ex.getMessage());
        }
        catch (JDOMException ex){
            return new ValidationResult(ValidationResult.Status.FATAL,
                    "Unexpected JDOM parse error occured : " + ex.getMessage());
        }
        catch (FileNotFoundException ex){
            return new ValidationResult(ValidationResult.Status.FATAL,
                    "Probably because relative (not absolute) reference to a resource :" + ex.getMessage());
        }
        catch(IOException ex){
            throw ex;
        }
        catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            ValidatorException ve = new ValidatorException(ex.getMessage());
            ve.setStackTrace(ex.getStackTrace());
            throw ve;
        }*/

        return null;

    }


    private void checkIfSupported(DataType dataType)
            throws ValidatorException
    {
        for (DataType dt : getSupportedValidationTypes()) {
            if (dt.equals(dataType)) {
                return;
            }
        }
        throw new ValidatorException(dataType);
    }
}