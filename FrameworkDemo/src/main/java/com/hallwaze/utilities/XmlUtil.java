package com.hallwaze.utilities;

import org.w3c.dom.Document;

public interface XmlUtil {

	Document convertToDocument(final Object xmlContent);
	
	String getXmlValue(Object xmlContent, String rootTag, String tagElement);
}
