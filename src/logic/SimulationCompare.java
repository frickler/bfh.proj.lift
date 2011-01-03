package logic;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl;

public class SimulationCompare {

	File[] resultFiles;
	File xslFile;
	File directory;

	public SimulationCompare(File currentDirectory, File[] selectedFiles,
			File xsltTransformation) {
		resultFiles = selectedFiles;
		directory = currentDirectory;
		xslFile = xsltTransformation;
	}

/**
 * compare the selected files with the xsl translation file
 *  and saves it at the same path the xsl-File was opened
 * @return The path where the comparision is saved.
 * @throws Exception
 */
	public String saveCompareResult() throws Exception {
		try {

			Document main = preapreDocument();
			
			Source source = new DOMSource(main);
			Source xsltSource = new StreamSource(xslFile);
			TransformerFactory transFact = TransformerFactory.newInstance();
			Transformer trans;
			trans = transFact.newTransformer(xsltSource);

			SimpleDateFormat dateformat = new SimpleDateFormat(
					"yyyy-MM-dd_hh_mm_ss");
			String pathName = xslFile.getParentFile() +"\\"+ dateformat.format(new Date())
					+ "_CompareResult.html";
			trans.transform(source, new StreamResult(pathName));

			return pathName;
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	private Document preapreDocument() throws ParserConfigurationException,
			SAXException, IOException {
		DocumentBuilder builder;
		builder = new DocumentBuilderFactoryImpl().newDocumentBuilder();
		Document main = builder.newDocument();
		Node compare = main.createElement("compare");

		for (File f : resultFiles) {

			Document document = builder.parse(f.getAbsoluteFile());
			Node importNode = main.importNode(document.getFirstChild(),
					true);
			compare.appendChild(importNode);
		}

		main.appendChild(compare);
		return main;
	}
}
