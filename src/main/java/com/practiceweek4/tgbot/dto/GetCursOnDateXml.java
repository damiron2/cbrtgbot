package com.practiceweek4.tgbot.dto;
import lombok.Data;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.datatype.XMLGregorianCalendar;

@Data
@XmlRootElement(name = "GetCursOnDateXML", namespace = "http://web.cbr.ru/")

public class GetCursOnDateXml {
    @XmlElement(name = "On_date", required = true,namespace = "http://web.cbr.ru/")
    protected XMLGregorianCalendar onDate;
}
