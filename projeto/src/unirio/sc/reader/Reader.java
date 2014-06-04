package unirio.sc.reader;

import java.io.File;
import java.io.IOException;

import javax.management.modelmbean.XMLParseException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import unirio.sc.reader.model.DependencyType;
import unirio.sc.reader.model.ElementType;
import unirio.sc.reader.model.ElementVisibility;
import unirio.sc.reader.model.Project;
import unirio.sc.reader.model.ProjectClass;
import unirio.sc.reader.model.ProjectPackage;


/**
 * Objetivos: Ler inst�ncias no formato odem e gerar um objeto Project com os dados
 * @author Marcio Barros
 */
public class Reader
{
	/**
	 * Carrega um arquivo XML para a mem�ria
	 */
	private static Document loadDocument(String filename) throws XMLParseException
	{
		File file = new File(filename);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try
		{
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			doc.getDocumentElement().normalize();
			return doc;
		}
		catch (ParserConfigurationException e)
		{
			throw new XMLParseException ("invalid XML content in file '" + filename + "'");
		}
		catch (SAXException e)
		{
			throw new XMLParseException ("invalid XML content in file '" + filename + "'");
		}
		catch (IOException e)
		{
			throw new XMLParseException ("unable to load file '" + filename + "'");
		}
	}

	/**
	 * Retorna o valor de um atributo de um elemento
	 */
	private static String getElementAttribute(Element element, String name) throws XMLParseException
	{
		String value = element.getAttribute(name);
		
		if (value == null)
			throw new XMLParseException("missing attribute '" + name + "' for element '" + element.getNodeName() + "'");
		
		return value;
	}

	/**
	 * Retorna o valor de um atributo de um elemento, usando um valor default em sua aus�ncia
	 */
	private static String getElementAttribute(Element element, String name, String defvalue)
	{
		String value = element.getAttribute(name);
		
		if (value == null)
			return defvalue;
		
		return value;
	}
	
	/**
	 * Carrega o primeiro filho de um elemento com uma determinada tag
	 */
	private static Element getFirstElement(Element element, String tag) throws XMLParseException
	{
		NodeList nodeList = element.getElementsByTagName(tag);

		if (nodeList.getLength() == 0)
			throw new XMLParseException("missing child tag '" + tag + "' under '" + element.getNodeName() + "'");

		return (Element) nodeList.item(0);		
	}
	
	/**
	 * Carrega as depend�ncias de uma classe
	 */
	private static void loadDependencies(ProjectClass aClass, Element element) throws XMLParseException
	{
		Element dependencyRoot = getFirstElement(element, "dependencies");
		NodeList nodeList = dependencyRoot.getElementsByTagName("depends-on");

		for (int i = 0; i < nodeList.getLength(); i++)
		{
			Element child = (Element)nodeList.item(i);
			String name = getElementAttribute(child, "name");
			String sClassification = getElementAttribute(child, "classification");
			
			DependencyType classification = DependencyType.fromIdentifier(sClassification);
			
			if (classification == null)
				throw new XMLParseException("invalid classification '" + sClassification + "' for dependency from '" + aClass.getName() + "' to '" + name + "'");

			aClass.addDependency(name, classification);
		}
	}

	/**
	 * Carrega as classes de um pacote
	 */
	private static void loadClasses(Project project, ProjectPackage apackage, Element element) throws XMLParseException
	{
		NodeList nodeList = element.getElementsByTagName("type");

		for (int i = 0; i < nodeList.getLength(); i++)
		{
			Element child = (Element)nodeList.item(i);
			String name = getElementAttribute(child, "name");
			String sClassification = getElementAttribute(child, "classification");
			String sVisibility = getElementAttribute(child, "visibility");
			String sAbstract = getElementAttribute(child, "isAbstract", "false");
			
			ElementType classification = ElementType.fromIdentifier(sClassification);
			
			if (classification == null)
				throw new XMLParseException("invalid classification '" + sClassification + "' for type '" + name + "'");
			
			ElementVisibility visibility = ElementVisibility.fromIdentifier(sVisibility);
			
			if (visibility == null)
				throw new XMLParseException("invalid visibility '" + sVisibility + "' for type '" + name + "'");
			
			ProjectClass aClass = new ProjectClass(name, classification, visibility, Boolean.parseBoolean(sAbstract));
			aClass.setPackage(apackage);
			project.addClass(aClass);
			loadDependencies(aClass, child);
		}
	}

	/**
	 * Carrega os pacotes da aplica��o
	 */
	private static void loadNamespaces(Project project, Element element) throws XMLParseException
	{
		NodeList nodeList = element.getElementsByTagName("namespace");

		for (int i = 0; i < nodeList.getLength(); i++)
		{
			Element child = (Element)nodeList.item(i);
			String name = getElementAttribute(child, "name");

			ProjectPackage apackage = project.addPackage(name);
			loadClasses(project, apackage, child);
		}
	}

	/**
	 * Carrega os containers da aplica��o
	 */
	private static void loadContainers(Project project, Element element) throws XMLParseException
	{
		NodeList nodeList = element.getElementsByTagName("container");

		for (int i = 0; i < nodeList.getLength(); i++)
			loadNamespaces(project, (Element)nodeList.item(i));
	}

	/**
	 * Carrega uma aplica��o a partir do elemento raiz do arquivo
	 */
	private static Project loadApplication(String filename, Element root) throws XMLParseException
	{
		Element element = getFirstElement(root, "context");
		String name = getElementAttribute(element, "name");
		Project application = new Project(filename, name);
		loadContainers(application, element);
		return application;
	}

	/**
	 * Carrega uma aplica��o a partir de um arquivo no formato XML ODEM
	 */
	public static Project execute(String filename) throws XMLParseException
	{
		Document doc = loadDocument(filename);

		if (doc == null)
			return null;

		return loadApplication(filename, doc.getDocumentElement());
	}
	
	/**
	 * Adaptado
	 */
	protected static Project loadModel(String nomeArquivo )
	throws Exception
	{
		Project modelo = null;
		try
		{
			modelo = execute( nomeArquivo );
		}
		catch ( XMLParseException eXML )
		{
			throw new Exception( "Erro na leitura do xml. msg=[" + eXML.getMessage( ) + "]");
		}
		return modelo;
	}
	
	/**
	 * Carrega os dados do problema a partir do arquivo
	 */
	public static Project load(String nomeArquivo)
	throws Exception
	{
		Project modelo = loadModel(nomeArquivo);
		
		if (modelo==null) throw new Exception("Erro ao carregar o arquivo " + nomeArquivo);

		return modelo;
	}
	
}