package br.ucsal.projetoAE4.uteis;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class LeitorXML {
	private Element raiz;

	public LeitorXML(String arquivoXML) throws IOException,
			ParserConfigurationException, SAXException {
		// fazer o parse do arquivo e criar o documento XML
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(arquivoXML);

		// obter o elemento raiz
		this.raiz = doc.getDocumentElement();
	}

	public NodeList obterListaNodes(String TagName) {
		NodeList listaNodes = raiz.getElementsByTagName(TagName);
		return listaNodes;
	}

	public String searchValorInAtributo(NodeList listaNodes, String attr,
			String Valor) {
		// obter os elementos de cada elemento contato
		Attr at = null;
		for (int i = 0; i < listaNodes.getLength(); i++) {
			// como cada elemento do NodeList é um nó, precisamos fazer o cast
			Element elem = (Element) listaNodes.item(i);
			// obter o atributo id do contato
			at = elem.getAttributeNode(attr);

			if (at.getNodeValue().equals(Valor)) {
				break;
			}
		}
		return at.getNodeValue();
	}

	public String getTextElementNameporPosicao(NodeList listaNodes,
			String nomeElemento, int PosicaoElemento) {
		// obter os elementos de cada elemento contato
		for (int i = 0; i < listaNodes.getLength(); i++) {
			// como cada elemento do NodeList é um nó, precisamos fazer o cast
			Element contato = (Element) listaNodes.item(i);

			if (i == (PosicaoElemento-1)) {
				NodeList listaNomes = contato.getElementsByTagName(nomeElemento);
				Node elem = listaNomes.item(0).getFirstChild();
				return elem.getNodeValue();
			}
		}
		return null;
	}
}