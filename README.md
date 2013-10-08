VTD Processor for Apache Hive XML SerDe
==============

Use the following DDL to create Apache Hive table, note the "xml.processor.class", 

CREATE [EXTERNAL] TABLE &lt;table_name&gt; (&lt;column_specifications&gt;)   
ROW FORMAT SERDE "com.ibm.spss.hive.serde2.xml.XmlSerDe"   
WITH SERDEPROPERTIES (   
"xml.processor.class"="com.ximpleware.hive.serde2.xml.vtd.XmlProcessor",   
"column.xpath.&lt;column_name&gt;"="&lt;xpath_query&gt;",   
... ["xml.map.specification.&lt;element_name&gt;"="&lt;map_specification&gt;"   
...   
])   
STORED AS   
INPUTFORMAT "com.ibm.spss.hive.serde2.xml.XmlInputFormat"   
OUTPUTFORMAT "org.apache.hadoop.hive.ql.io.IgnoreKeyTextOutputFormat"   
[LOCATION "&lt;data_location&gt;"]   
TBLPROPERTIES (   
"xmlinput.start"="&lt;start_tag ",   
"xmlinput.end"="&lt;end_tag&gt;"  
);   

See https://github.com/dvasilen/Hive-XML-SerDe for further details.

# Important notice : License

VTD-XML use a dual GPLv2/commercial license.

This is the reason why Hive-XML-SerDe-VTD is a separate project licensed under 
GPLv2 instead of being integrated into core Hive-XML-SerDe, that is Apache 2 licensed.

So please be aware that if you use this module :

* either your whole Hive-XML-SerDe based project become GPLv2,
* or you have to purchase a commercial license from [Ximpleware](http://www.ximpleware.com).