package com.practiceweek4.tgbot.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "GetCursOnDateXMLResponse", namespace = "http://web.cbr.ru/")
@Data
public class GetCursOnDateXmlResponse {
    @XmlElement(name = "GetCursOnDateXMLResult", namespace = "http://web.cbr.ru/")
    private GetCursOnDateXmlResult getCursOnDateXmlResult;

}
