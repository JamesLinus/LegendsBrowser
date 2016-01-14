package legends.xml.handlers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ListContentHandler extends StackContentHandler {
	private AnnotationContentHandler elementContentHandler;
	private List<Object> elements = new ArrayList<>();

	public ListContentHandler(String name, AnnotationContentHandler elementContentHandler) {
		super(name);
		this.elementContentHandler = elementContentHandler;
		if (elementContentHandler.getConsumer() == null) {
			elementContentHandler.setConsumer(v -> getElements().add(v));
		}
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
		if (localName.equals(elementContentHandler.getName())) {
			pushContentHandler(elementContentHandler);
		} else {
			System.out.println(name + " - unknown list element: " + localName);
		}
	}

	@Override
	protected void consume() {
		try {
			if (consumer != null)
				consumer.accept(elements);
		} catch (InvocationTargetException | IllegalAccessException | IllegalArgumentException e) {
			e.printStackTrace();
		}
		elements = new ArrayList<>();
	}

	public List<Object> getElements() {
		return elements;
	}

}
