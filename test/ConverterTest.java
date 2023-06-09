import com.google.gson.*;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;

import org.w3c.dom.*;

import javax.xml.parsers.*;
import java.io.*;

import java.math.BigDecimal;
import java.util.*;

class Clue {
    String answer;
    String input;

    Clue(String answer, String input) {
        this.answer = answer.strip();
        this.input = input.strip();
    }
}

public class ConverterTest extends StageTest<Clue> {

    static Map<String, String> allTests;

    static {
        allTests = new LinkedHashMap<>();

        allTests.put(
            "{\n" +
                "    \"transactions\": {\n" +
                "        \"id\": \"6753322\",\n" +
                "        \"data\": [\n" +
                "            124,\n" +
                "            true,\n" +
                "            false,\n" +
                "            [ ],\n" +
                "            [],\n" +
                "            { },\n" +
                "            {},\n" +
                "            [\n" +
                "                1, 2, 3,\n" +
                "                {\n" +
                "                    \"@attr\": \"value6\",\n" +
                "                    \"#element\": \"value7\"\n" +
                "                }\n" +
                "            ],\n" +
                "            null,\n" +
                "            \"\",\n" +
                "            {\n" +
                "                \"key1\": \"value1\",\n" +
                "                \"key2\": {\n" +
                "                    \"@attr\": \"value2\",\n" +
                "                    \"#key2\": \"value3\"\n" +
                "                }\n" +
                "            },\n" +
                "            {\n" +
                "                \"@attr2\": \"value4\",\n" +
                "                \"#element\": \"value5\"\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}",


            "<transactions>\n" +
                "    <id>6753322</id>\n" +
                "    <data>\n" +
                "        <element>124</element>\n" +
                "        <element>true</element>\n" +
                "        <element>false</element>\n" +
                "        <element></element>\n" +
                "        <element></element>\n" +
                "        <element></element>\n" +
                "        <element></element>\n" +
                "        <element>\n" +
                "            <element>1</element>\n" +
                "            <element>2</element>\n" +
                "            <element>3</element>\n" +
                "            <element attr=\"value6\">value7</element>\n" +
                "        </element>\n" +
                "        <element />\n" +
                "        <element></element>\n" +
                "        <element>\n" +
                "            <key1>value1</key1>\n" +
                "            <key2 attr=\"value2\">value3</key2>\n" +
                "        </element>\n" +
                "        <element attr2=\"value4\">value5</element>\n" +
                "    </data>\n" +
                "</transactions>"
        );



        allTests.put(
            "<?xml version = \"1.0\" encoding = \"utf-8\"?>\n" +
                "<transactions>\n" +
                "    <transaction>\n" +
                "        <id>6753323</id>\n" +
                "        <number region = \"Russia\">8-900-000-00-00</number>\n" +
                "        <date day = \"12\" month = \"12\" year = \"2018\"/>\n" +
                "        <amount currency=\"EUR\">1000.00</amount>\n" +
                "        <completed>true</completed>\n" +
                "    </transaction>\n" +
                "    <transaction>\n" +
                "        <id>67533244</id>\n" +
                "        <number region = \"Russia\">8-900-000-00-01</number>\n" +
                "        <date day = \"13\" month = \"12\" year = \"2018\"/>\n" +
                "        <amount currency =\"RUB\">2000.00</amount>\n" +
                "        <completed>true</completed>\n" +
                "    </transaction>\n" +
                "    <transaction>\n" +
                "        <id>67533257</id>\n" +
                "        <number region=\"Russia\">8-900-000-00-02</number>\n" +
                "        <date day = \"14\" month = \"12\" year = \"2018\"/>\n" +
                "        <amount currency = \"EUR\">3000.00</amount>\n" +
                "        <completed>false</completed>\n" +
                "    </transaction>\n" +
                "    <transaction>\n" +
                "        <id>67533259</id>\n" +
                "        <number region = \"Ukraine\">8-900-000-00-03</number>\n" +
                "        <date day = \"15\" month = \"12\" year = \"2018\"/>\n" +
                "        <amount currency = \"GRN\">4000.00</amount>\n" +
                "        <completed>false</completed>\n" +
                "    </transaction>\n" +
                "    <transaction>\n" +
                "        <id>67533566</id>\n" +
                "        <number region = \"Ukraine\">8-900-000-00-04</number>\n" +
                "        <date day = \"16\" month = \"12\" year = \"2018\"/>\n" +
                "        <amount currency = \"USD\">5000.00</amount>\n" +
                "        <completed>false</completed>\n" +
                "    </transaction>\n" +
                "</transactions>",


            "{\n" +
                "    \"transactions\" : [\n" +
                "        {\n" +
                "            \"id\" : \"6753323\",\n" +
                "            \"number\" : {\n" +
                "                \"@region\" : \"Russia\",\n" +
                "                \"#number\" : \"8-900-000-00-00\"\n" +
                "            },\n" +
                "            \"date\" : {\n" +
                "                \"@day\" : \"12\",\n" +
                "                \"@month\" : \"12\",\n" +
                "                \"@year\" : \"2018\",\n" +
                "                \"#date\" : null\n" +
                "            },\n" +
                "            \"amount\" : {\n" +
                "                \"@currency\" : \"EUR\",\n" +
                "                \"#amount\" : \"1000.00\"\n" +
                "            },\n" +
                "            \"completed\" : \"true\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\" : \"67533244\",\n" +
                "            \"number\" : {\n" +
                "                \"@region\" : \"Russia\",\n" +
                "                \"#number\" : \"8-900-000-00-01\"\n" +
                "            },\n" +
                "            \"date\" : {\n" +
                "                \"@day\" : \"13\",\n" +
                "                \"@month\" : \"12\",\n" +
                "                \"@year\" : \"2018\",\n" +
                "                \"#date\" : null\n" +
                "            },\n" +
                "            \"amount\" : {\n" +
                "                \"@currency\" : \"RUB\",\n" +
                "                \"#amount\" : \"2000.00\"\n" +
                "            },\n" +
                "            \"completed\" : \"true\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\" : \"67533257\",\n" +
                "            \"number\" : {\n" +
                "                \"@region\" : \"Russia\",\n" +
                "                \"#number\" : \"8-900-000-00-02\"\n" +
                "            },\n" +
                "            \"date\" : {\n" +
                "                \"@day\" : \"14\",\n" +
                "                \"@month\" : \"12\",\n" +
                "                \"@year\" : \"2018\",\n" +
                "                \"#date\" : null\n" +
                "            },\n" +
                "            \"amount\" : {\n" +
                "                \"@currency\" : \"EUR\",\n" +
                "                \"#amount\" : \"3000.00\"\n" +
                "            },\n" +
                "            \"completed\" : \"false\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\" : \"67533259\",\n" +
                "            \"number\" : {\n" +
                "                \"@region\" : \"Ukraine\",\n" +
                "                \"#number\" : \"8-900-000-00-03\"\n" +
                "            },\n" +
                "            \"date\" : {\n" +
                "                \"@day\" : \"15\",\n" +
                "                \"@month\" : \"12\",\n" +
                "                \"@year\" : \"2018\",\n" +
                "                \"#date\" : null\n" +
                "            },\n" +
                "            \"amount\" : {\n" +
                "                \"@currency\" : \"GRN\",\n" +
                "                \"#amount\" : \"4000.00\"\n" +
                "            },\n" +
                "            \"completed\" : \"false\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\" : \"67533566\",\n" +
                "            \"number\" : {\n" +
                "                \"@region\" : \"Ukraine\",\n" +
                "                \"#number\" : \"8-900-000-00-04\"\n" +
                "            },\n" +
                "            \"date\" : {\n" +
                "                \"@day\" : \"16\",\n" +
                "                \"@month\" : \"12\",\n" +
                "                \"@year\" : \"2018\",\n" +
                "                \"#date\" : null\n" +
                "            },\n" +
                "            \"amount\" : {\n" +
                "                \"@currency\" : \"USD\",\n" +
                "                \"#amount\" : \"5000.00\"\n" +
                "            },\n" +
                "            \"completed\" : \"false\"\n" +
                "        }\n" +
                "    ]\n" +
                "}"
        );



        allTests.put(
            "{\n" +
                "    \"transaction\": {\n" +
                "        \"id\": \"6753324\",\n" +
                "        \"number\": {\n" +
                "            \"@region\": \"Russia\",\n" +
                "            \"#number\": \"8-900-000-000\"\n" +
                "        },\n" +
                "        \"special1\": false,\n" +
                "        \"special2\": true,\n" +
                "        \"empty1\": null,\n" +
                "        \"empty2\": { },\n" +
                "        \"empty3\": [ ],\n" +
                "        \"empty4\": {},\n" +
                "        \"empty5\": [],\n" +
                "        \"empty6\": {\n" +
                "\n" +
                "        },\n" +
                "        \"empty7\": [\n" +
                "\n" +
                "        ],\n" +
                "        \"empty8\": \"\",\n" +
                "        \"array1\": [\n" +
                "            null, null\n" +
                "        ],\n" +
                "        \"array2\": [\n" +
                "            [],\n" +
                "            true, false, null,\n" +
                "            123, 123.456,\n" +
                "            \"\",\n" +
                "            {\n" +
                "                \"key1\": \"value1\",\n" +
                "                \"key2\": {\n" +
                "                    \"@attr\": \"value2\",\n" +
                "                    \"#key2\": \"value3\"\n" +
                "                }\n" +
                "            },\n" +
                "            {\n" +
                "                \"@attr2\": \"value4\",\n" +
                "                \"#element\": \"value5\"\n" +
                "            }\n" +
                "            ,\n" +
                "            {\n" +
                "                \"@attr3\": \"value4\",\n" +
                "                \"#elem\": \"value5\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"#element\": null\n" +
                "            },\n" +
                "            {\n" +
                "                \"#element\": {\n" +
                "                    \"deep\": {\n" +
                "                        \"@deepattr\": \"deepvalue\",\n" +
                "                        \"#deep\": [\n" +
                "                            1, 2, 3\n" +
                "                        ]\n" +
                "                    }\n" +
                "                }\n" +
                "            }\n" +
                "        ],\n" +
                "        \"inner1\": {\n" +
                "            \"inner2\": {\n" +
                "                \"inner3\": {\n" +
                "                    \"key1\": \"value1\",\n" +
                "                    \"key2\": \"value2\"\n" +
                "                }\n" +
                "            }\n" +
                "        },\n" +
                "        \"inner4\": {\n" +
                "            \"@\": 123,\n" +
                "            \"#inner4\": \"value3\"\n" +
                "        },\n" +
                "        \"inner5\": {\n" +
                "            \"@attr1\": 123.456,\n" +
                "            \"#inner4\": \"value4\"\n" +
                "        },\n" +
                "        \"inner6\": {\n" +
                "            \"@attr2\": 789.321,\n" +
                "            \"#inner6\": \"value5\"\n" +
                "        },\n" +
                "        \"inner7\": {\n" +
                "            \"#inner7\": \"value6\"\n" +
                "        },\n" +
                "        \"inner8\": {\n" +
                "            \"@attr3\": \"value7\"\n" +
                "        },\n" +
                "        \"inner9\": {\n" +
                "            \"@attr4\": \"value8\",\n" +
                "            \"#inner9\": \"value9\",\n" +
                "            \"something\": \"value10\"\n" +
                "        },\n" +
                "        \"inner10\": {\n" +
                "            \"@attr5\": null,\n" +
                "            \"#inner10\": null\n" +
                "        },\n" +
                "        \"inner11\": {\n" +
                "            \"@attr11\": \"value11\",\n" +
                "            \"#inner11\": {\n" +
                "                \"inner12\": {\n" +
                "                    \"@attr12\": \"value12\",\n" +
                "                    \"#inner12\": {\n" +
                "                        \"inner13\": {\n" +
                "                            \"@attr13\": \"value13\",\n" +
                "                            \"#inner13\": {\n" +
                "                                \"inner14\": \"v14\"\n" +
                "                            }\n" +
                "                        }\n" +
                "                    }\n" +
                "                }\n" +
                "            }\n" +
                "        },\n" +
                "        \"inner15\": {\n" +
                "            \"@\": null,\n" +
                "            \"#\": null\n" +
                "        },\n" +
                "        \"inner16\": {\n" +
                "            \"@somekey\": \"attrvalue\",\n" +
                "            \"#inner16\": null,\n" +
                "            \"somekey\": \"keyvalue\",\n" +
                "            \"inner16\": \"notnull\"\n" +
                "        },\n" +
                "        \"crazyattr1\": {\n" +
                "            \"@attr1\": 123,\n" +
                "            \"#crazyattr1\": \"v15\"\n" +
                "        },\n" +
                "        \"crazyattr2\": {\n" +
                "            \"@attr1\": 123.456,\n" +
                "            \"#crazyattr2\": \"v16\"\n" +
                "        },\n" +
                "        \"crazyattr3\": {\n" +
                "            \"@attr1\": null,\n" +
                "            \"#crazyattr3\": \"v17\"\n" +
                "        },\n" +
                "        \"crazyattr4\": {\n" +
                "            \"@attr1\": true,\n" +
                "            \"#crazyattr4\": \"v18\"\n" +
                "        },\n" +
                "        \"crazyattr5\": {\n" +
                "            \"@attr1\": false,\n" +
                "            \"#crazyattr5\": \"v19\"\n" +
                "        },\n" +
                "        \"crazyattr6\": {\n" +
                "            \"@attr1\": \"\",\n" +
                "            \"#crazyattr6\": \"v20\"\n" +
                "        },\n" +
                "        \"crazyattr7\": {\n" +
                "            \"@attr1\": {},\n" +
                "            \"#crazyattr7\": \"v21\"\n" +
                "        },\n" +
                "        \"crazyattr9\": {\n" +
                "            \"@attr1\": {\n" +
                "                \"@\": 1,\n" +
                "                \"#\": 2,\n" +
                "                \"\": 3,\n" +
                "                \"key\": 4\n" +
                "            },\n" +
                "            \"#crazyattr9\": \"v23\"\n" +
                "        },\n" +
                "        \"crazyattr10\": {\n" +
                "            \"@attr1\": [],\n" +
                "            \"#crazyattr10\": \"v24\"\n" +
                "        },\n" +
                "        \"crazyattr11\": {\n" +
                "            \"attr1\": \"better\",\n" +
                "            \"@attr1\": {\n" +
                "                \"key9\": \"value9\"\n" +
                "            },\n" +
                "            \"#crazyattr11\": \"v25\"\n" +
                "        },\n" +
                "        \"crazyattr12\": {\n" +
                "            \"@attr1\": [\n" +
                "                \"\"\n" +
                "            ],\n" +
                "            \"#crazyattr12\": \"v26\"\n" +
                "        },\n" +
                "        \"\": {\n" +
                "            \"#\": null,\n" +
                "            \"secret\": \"won't be converted\"\n" +
                "        },\n" +
                "        \"@\": 123,\n" +
                "        \"#\": [\n" +
                "            1, 2, 3\n" +
                "        ]\n" +
                "    },\n" +
                "    \"meta\": {\n" +
                "        \"version\": 0.01\n" +
                "    }\n" +
                "}",


            "<root>\n" +
                "    <transaction>\n" +
                "        <id>6753324</id>\n" +
                "        <number region=\"Russia\">8-900-000-000</number>\n" +
                "        <special1>false</special1>\n" +
                "        <special2>true</special2>\n" +
                "        <empty1 />\n" +
                "        <empty2></empty2>\n" +
                "        <empty3></empty3>\n" +
                "        <empty4></empty4>\n" +
                "        <empty5></empty5>\n" +
                "        <empty6></empty6>\n" +
                "        <empty7></empty7>\n" +
                "        <empty8></empty8>\n" +
                "        <array1>\n" +
                "            <element />\n" +
                "            <element />\n" +
                "        </array1>\n" +
                "        <array2>\n" +
                "            <element></element>\n" +
                "            <element>true</element>\n" +
                "            <element>false</element>\n" +
                "            <element />\n" +
                "            <element>123</element>\n" +
                "            <element>123.456</element>\n" +
                "            <element></element>\n" +
                "            <element>\n" +
                "                <key1>value1</key1>\n" +
                "                <key2 attr=\"value2\">value3</key2>\n" +
                "            </element>\n" +
                "            <element attr2=\"value4\">value5</element>\n" +
                "            <element>\n" +
                "                <attr3>value4</attr3>\n" +
                "                <elem>value5</elem>\n" +
                "            </element>\n" +
                "            <element />\n" +
                "            <element>\n" +
                "                <deep deepattr=\"deepvalue\">\n" +
                "                    <element>1</element>\n" +
                "                    <element>2</element>\n" +
                "                    <element>3</element>\n" +
                "                </deep>\n" +
                "            </element>\n" +
                "        </array2>\n" +
                "        <inner1>\n" +
                "            <inner2>\n" +
                "                <inner3>\n" +
                "                    <key1>value1</key1>\n" +
                "                    <key2>value2</key2>\n" +
                "                </inner3>\n" +
                "            </inner2>\n" +
                "        </inner1>\n" +
                "        <inner4>\n" +
                "            <inner4>value3</inner4>\n" +
                "        </inner4>\n" +
                "        <inner5>\n" +
                "            <attr1>123.456</attr1>\n" +
                "            <inner4>value4</inner4>\n" +
                "        </inner5>\n" +
                "        <inner6 attr2=\"789.321\">value5</inner6>\n" +
                "        <inner7>value6</inner7>\n" +
                "        <inner8>\n" +
                "            <attr3>value7</attr3>\n" +
                "        </inner8>\n" +
                "        <inner9>\n" +
                "            <attr4>value8</attr4>\n" +
                "            <inner9>value9</inner9>\n" +
                "            <something>value10</something>\n" +
                "        </inner9>\n" +
                "        <inner10 attr5=\"\" />\n" +
                "        <inner11 attr11=\"value11\">\n" +
                "            <inner12 attr12=\"value12\">\n" +
                "                <inner13 attr13=\"value13\">\n" +
                "                    <inner14>v14</inner14>\n" +
                "                </inner13>\n" +
                "            </inner12>\n" +
                "        </inner11>\n" +
                "        <inner15></inner15>\n" +
                "        <inner16>\n" +
                "            <somekey>keyvalue</somekey>\n" +
                "            <inner16>notnull</inner16>\n" +
                "        </inner16>\n" +
                "        <crazyattr1 attr1=\"123\">v15</crazyattr1>\n" +
                "        <crazyattr2 attr1=\"123.456\">v16</crazyattr2>\n" +
                "        <crazyattr3 attr1=\"\">v17</crazyattr3>\n" +
                "        <crazyattr4 attr1=\"true\">v18</crazyattr4>\n" +
                "        <crazyattr5 attr1=\"false\">v19</crazyattr5>\n" +
                "        <crazyattr6 attr1=\"\">v20</crazyattr6>\n" +
                "        <crazyattr7 attr1=\"\">v21</crazyattr7>\n" +
                "        <crazyattr9>\n" +
                "            <attr1>\n" +
                "                <key>4</key>\n" +
                "            </attr1>\n" +
                "            <crazyattr9>v23</crazyattr9>\n" +
                "        </crazyattr9>\n" +
                "        <crazyattr10 attr1=\"\">v24</crazyattr10>\n" +
                "        <crazyattr11>\n" +
                "            <attr1>better</attr1>\n" +
                "            <crazyattr11>v25</crazyattr11>\n" +
                "        </crazyattr11>\n" +
                "        <crazyattr12>\n" +
                "            <attr1>\n" +
                "                <element></element>\n" +
                "            </attr1>\n" +
                "            <crazyattr12>v26</crazyattr12>\n" +
                "        </crazyattr12>\n" +
                "    </transaction>\n" +
                "    <meta>\n" +
                "        <version>0.01</version>\n" +
                "    </meta>\n" +
                "</root>"
        );


        allTests.put(
            "<root>\n" +
                "    <transaction>\n" +
                "        <id>6753325</id>\n" +
                "        <number region='Russia'>8-900-000-000</number>\n" +
                "        <special1>false</special1>\n" +
                "        <special2>true</special2>\n" +
                "        <empty1 />\n" +
                "        <empty2></empty2>\n" +
                "        <array1>\n" +
                "            <element />\n" +
                "            <element />\n" +
                "        </array1>\n" +
                "        <array2>\n" +
                "            <element></element>\n" +
                "            <element />\n" +
                "            <element>123</element>\n" +
                "            <element>123.456</element>\n" +
                "            <element>\n" +
                "                <key1>value1</key1>\n" +
                "                <key2 attr=\"value2\">value3</key2>\n" +
                "            </element>\n" +
                "            <element attr2='value4'>value5</element>\n" +
                "            <element>\n" +
                "                <attr3>value4</attr3>\n" +
                "                <elem>value5</elem>\n" +
                "            </element>\n" +
                "            <element>\n" +
                "                <deep deepattr=\"deepvalue\">\n" +
                "                    <element>1</element>\n" +
                "                    <element>2</element>\n" +
                "                    <element>3</element>\n" +
                "                </deep>\n" +
                "            </element>\n" +
                "        </array2>\n" +
                "        <inner1>\n" +
                "            <inner2>\n" +
                "                <inner3>\n" +
                "                    <key1>value1</key1>\n" +
                "                    <key2>value2</key2>\n" +
                "                </inner3>\n" +
                "            </inner2>\n" +
                "        </inner1>\n" +
                "        <inner4>\n" +
                "            <inner4>value3</inner4>\n" +
                "        </inner4>\n" +
                "        <inner5>\n" +
                "            <attr1>123.456</attr1>\n" +
                "            <inner4>value4</inner4>\n" +
                "        </inner5>\n" +
                "        <inner6 attr2=\"789.321\">value5</inner6>\n" +
                "        <inner7>value6</inner7>\n" +
                "        <inner8>\n" +
                "            <attr3>value7</attr3>\n" +
                "        </inner8>\n" +
                "        <inner9>\n" +
                "            <attr4>value8</attr4>\n" +
                "            <inner9>value9</inner9>\n" +
                "            <something>value10</something>\n" +
                "        </inner9>\n" +
                "        <inner10 attr5='' />\n" +
                "        <inner11 attr11=\"value11\">\n" +
                "            <inner12 attr12=\"value12\">\n" +
                "                <inner13 attr13=\"value13\">\n" +
                "                    <inner14>v14</inner14>\n" +
                "                </inner13>\n" +
                "            </inner12>\n" +
                "        </inner11>\n" +
                "        <inner15></inner15>\n" +
                "        <inner16>\n" +
                "            <somekey>keyvalue</somekey>\n" +
                "            <inner16>notnull</inner16>\n" +
                "        </inner16>\n" +
                "        <crazyattr1 attr1='123'>v15</crazyattr1>\n" +
                "        <crazyattr2 attr1=\"123.456\">v16</crazyattr2>\n" +
                "        <crazyattr3 attr1=''>v17</crazyattr3>\n" +
                "        <crazyattr9>\n" +
                "            <attr1>\n" +
                "                <key>4</key>\n" +
                "            </attr1>\n" +
                "            <crazyattr9>v23</crazyattr9>\n" +
                "        </crazyattr9>\n" +
                "    </transaction>\n" +
                "    <meta>\n" +
                "        <version>0.01</version>\n" +
                "    </meta>\n" +
                "</root>",


            "{\n" +
                "    \"root\": {\n" +
                "        \"transaction\": {\n" +
                "            \"id\": \"6753325\",\n" +
                "            \"number\": {\n" +
                "                \"@region\": \"Russia\",\n" +
                "                \"#number\": \"8-900-000-000\"\n" +
                "            },\n" +
                "            \"special1\": \"false\",\n" +
                "            \"special2\": \"true\",\n" +
                "            \"empty1\": null,\n" +
                "            \"empty2\": \"\",\n" +
                "            \"array1\": [\n" +
                "                null, null\n" +
                "            ],\n" +
                "            \"array2\": [\n" +
                "                \"\",\n" +
                "                null,\n" +
                "                \"123\",\n" +
                "                \"123.456\",\n" +
                "                {\n" +
                "                    \"key1\": \"value1\",\n" +
                "                    \"key2\": {\n" +
                "                        \"@attr\": \"value2\",\n" +
                "                        \"#key2\": \"value3\"\n" +
                "                    }\n" +
                "                },\n" +
                "                {\n" +
                "                    \"@attr2\": \"value4\",\n" +
                "                    \"#element\": \"value5\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"attr3\": \"value4\",\n" +
                "                    \"elem\": \"value5\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"deep\": {\n" +
                "                        \"@deepattr\": \"deepvalue\",\n" +
                "                        \"#deep\": [\n" +
                "                            \"1\",\n" +
                "                            \"2\",\n" +
                "                            \"3\"\n" +
                "                        ]\n" +
                "                    }\n" +
                "                }\n" +
                "            ],\n" +
                "            \"inner1\": {\n" +
                "                \"inner2\": {\n" +
                "                    \"inner3\": {\n" +
                "                        \"key1\": \"value1\",\n" +
                "                        \"key2\": \"value2\"\n" +
                "                    }\n" +
                "                }\n" +
                "            },\n" +
                "            \"inner4\": {\n" +
                "                \"inner4\": \"value3\"\n" +
                "            },\n" +
                "            \"inner5\": {\n" +
                "                \"attr1\": \"123.456\",\n" +
                "                \"inner4\": \"value4\"\n" +
                "            },\n" +
                "            \"inner6\": {\n" +
                "                \"@attr2\": \"789.321\",\n" +
                "                \"#inner6\": \"value5\"\n" +
                "            },\n" +
                "            \"inner7\": \"value6\",\n" +
                "            \"inner8\": {\n" +
                "                \"attr3\": \"value7\"\n" +
                "            },\n" +
                "            \"inner9\": {\n" +
                "                \"attr4\": \"value8\",\n" +
                "                \"inner9\": \"value9\",\n" +
                "                \"something\": \"value10\"\n" +
                "            },\n" +
                "            \"inner10\": {\n" +
                "                \"@attr5\": \"\",\n" +
                "                \"#inner10\": null\n" +
                "            },\n" +
                "            \"inner11\": {\n" +
                "                \"@attr11\": \"value11\",\n" +
                "                \"#inner11\": {\n" +
                "                    \"inner12\": {\n" +
                "                        \"@attr12\": \"value12\",\n" +
                "                        \"#inner12\": {\n" +
                "                            \"inner13\": {\n" +
                "                                \"@attr13\": \"value13\",\n" +
                "                                \"#inner13\": {\n" +
                "                                    \"inner14\": \"v14\"\n" +
                "                                }\n" +
                "                            }\n" +
                "                        }\n" +
                "                    }\n" +
                "                }\n" +
                "            },\n" +
                "            \"inner15\": \"\",\n" +
                "            \"inner16\": {\n" +
                "                \"somekey\": \"keyvalue\",\n" +
                "                \"inner16\": \"notnull\"\n" +
                "            },\n" +
                "            \"crazyattr1\": {\n" +
                "                \"@attr1\": \"123\",\n" +
                "                \"#crazyattr1\": \"v15\"\n" +
                "            },\n" +
                "            \"crazyattr2\": {\n" +
                "                \"@attr1\": \"123.456\",\n" +
                "                \"#crazyattr2\": \"v16\"\n" +
                "            },\n" +
                "            \"crazyattr3\": {\n" +
                "                \"@attr1\": \"\",\n" +
                "                \"#crazyattr3\": \"v17\"\n" +
                "            },\n" +
                "            \"crazyattr9\": {\n" +
                "                \"attr1\": {\n" +
                "                    \"key\": \"4\"\n" +
                "                },\n" +
                "                \"crazyattr9\": \"v23\"\n" +
                "            }\n" +
                "        },\n" +
                "        \"meta\": {\n" +
                "            \"version\": \"0.01\"\n" +
                "        }\n" +
                "    }\n" +
                "}"
        );


        allTests.put(
            "{\"transaction\":{\"id\":\"6753326\",\"number\":{\"@region\":\"Russia\",\"#number\":\"8-900-000-000\"},\"special1\":false,\"special2\":true,\"empty1\":null,\"empty2\":{},\"empty3\":[],\"empty4\":{},\"empty5\":[],\"empty6\":{},\"empty7\":[],\"empty8\":\"\",\"array1\":[null,null],\"array2\":[[],true,false,null,123,123.456,\"\",{\"key1\":\"value1\",\"key2\":{\"@attr\":\"value2\",\"#key2\":\"value3\"}},{\"@attr2\":\"value4\",\"#element\":\"value5\"},{\"@attr3\":\"value4\",\"#elem\":\"value5\"},{\"#element\":null},{\"#element\":{\"deep\":{\"@deepattr\":\"deepvalue\",\"#deep\":[1,2,3]}}}],\"inner1\":{\"inner2\":{\"inner3\":{\"key1\":\"value1\",\"key2\":\"value2\"}}},\"inner4\":{\"@\":123,\"#inner4\":\"value3\"},\"inner5\":{\"@attr1\":123.456,\"#inner4\":\"value4\"},\"inner6\":{\"@attr2\":789.321,\"#inner6\":\"value5\"},\"inner7\":{\"#inner7\":\"value6\"},\"inner8\":{\"@attr3\":\"value7\"},\"inner9\":{\"@attr4\":\"value8\",\"#inner9\":\"value9\",\"something\":\"value10\"},\"inner10\":{\"@attr5\":null,\"#inner10\":null},\"inner11\":{\"@attr11\":\"value11\",\"#inner11\":{\"inner12\":{\"@attr12\":\"value12\",\"#inner12\":{\"inner13\":{\"@attr13\":\"value13\",\"#inner13\":{\"inner14\":\"v14\"}}}}}},\"inner15\":{\"@\":null,\"#\":null},\"inner16\":{\"@somekey\":\"attrvalue\",\"#inner16\":null,\"somekey\":\"keyvalue\",\"inner16\":\"notnull\"},\"crazyattr1\":{\"@attr1\":123,\"#crazyattr1\":\"v15\"},\"crazyattr2\":{\"@attr1\":123.456,\"#crazyattr2\":\"v16\"},\"crazyattr3\":{\"@attr1\":null,\"#crazyattr3\":\"v17\"},\"crazyattr4\":{\"@attr1\":true,\"#crazyattr4\":\"v18\"},\"crazyattr5\":{\"@attr1\":false,\"#crazyattr5\":\"v19\"},\"crazyattr6\":{\"@attr1\":\"\",\"#crazyattr6\":\"v20\"},\"crazyattr7\":{\"@attr1\":{},\"#crazyattr7\":\"v21\"},\"crazyattr9\":{\"@attr1\":{\"@\":1,\"#\":2,\"\":3,\"key\":4},\"#crazyattr9\":\"v23\"},\"crazyattr10\":{\"@attr1\":[],\"#crazyattr10\":\"v24\"},\"crazyattr11\":{\"attr1\":\"better\",\"@attr1\":{\"key9\":\"value9\"},\"#crazyattr11\":\"v25\"},\"crazyattr12\":{\"@attr1\":[\"\"],\"#crazyattr12\":\"v26\"},\"\":{\"#\":null,\"secret\":\"won't be converted\"},\"@\":123,\"#\":[1,2,3]},\"meta\":{\"version\":0.01}}",


            "<root>\n" +
                "    <transaction>\n" +
                "        <id>6753326</id>\n" +
                "        <number region=\"Russia\">8-900-000-000</number>\n" +
                "        <special1>false</special1>\n" +
                "        <special2>true</special2>\n" +
                "        <empty1 />\n" +
                "        <empty2></empty2>\n" +
                "        <empty3></empty3>\n" +
                "        <empty4></empty4>\n" +
                "        <empty5></empty5>\n" +
                "        <empty6></empty6>\n" +
                "        <empty7></empty7>\n" +
                "        <empty8></empty8>\n" +
                "        <array1>\n" +
                "            <element />\n" +
                "            <element />\n" +
                "        </array1>\n" +
                "        <array2>\n" +
                "            <element></element>\n" +
                "            <element>true</element>\n" +
                "            <element>false</element>\n" +
                "            <element />\n" +
                "            <element>123</element>\n" +
                "            <element>123.456</element>\n" +
                "            <element></element>\n" +
                "            <element>\n" +
                "                <key1>value1</key1>\n" +
                "                <key2 attr=\"value2\">value3</key2>\n" +
                "            </element>\n" +
                "            <element attr2=\"value4\">value5</element>\n" +
                "            <element>\n" +
                "                <attr3>value4</attr3>\n" +
                "                <elem>value5</elem>\n" +
                "            </element>\n" +
                "            <element />\n" +
                "            <element>\n" +
                "                <deep deepattr=\"deepvalue\">\n" +
                "                    <element>1</element>\n" +
                "                    <element>2</element>\n" +
                "                    <element>3</element>\n" +
                "                </deep>\n" +
                "            </element>\n" +
                "        </array2>\n" +
                "        <inner1>\n" +
                "            <inner2>\n" +
                "                <inner3>\n" +
                "                    <key1>value1</key1>\n" +
                "                    <key2>value2</key2>\n" +
                "                </inner3>\n" +
                "            </inner2>\n" +
                "        </inner1>\n" +
                "        <inner4>\n" +
                "            <inner4>value3</inner4>\n" +
                "        </inner4>\n" +
                "        <inner5>\n" +
                "            <attr1>123.456</attr1>\n" +
                "            <inner4>value4</inner4>\n" +
                "        </inner5>\n" +
                "        <inner6 attr2=\"789.321\">value5</inner6>\n" +
                "        <inner7>value6</inner7>\n" +
                "        <inner8>\n" +
                "            <attr3>value7</attr3>\n" +
                "        </inner8>\n" +
                "        <inner9>\n" +
                "            <attr4>value8</attr4>\n" +
                "            <inner9>value9</inner9>\n" +
                "            <something>value10</something>\n" +
                "        </inner9>\n" +
                "        <inner10 attr5=\"\" />\n" +
                "        <inner11 attr11=\"value11\">\n" +
                "            <inner12 attr12=\"value12\">\n" +
                "                <inner13 attr13=\"value13\">\n" +
                "                    <inner14>v14</inner14>\n" +
                "                </inner13>\n" +
                "            </inner12>\n" +
                "        </inner11>\n" +
                "        <inner15></inner15>\n" +
                "        <inner16>\n" +
                "            <somekey>keyvalue</somekey>\n" +
                "            <inner16>notnull</inner16>\n" +
                "        </inner16>\n" +
                "        <crazyattr1 attr1=\"123\">v15</crazyattr1>\n" +
                "        <crazyattr2 attr1=\"123.456\">v16</crazyattr2>\n" +
                "        <crazyattr3 attr1=\"\">v17</crazyattr3>\n" +
                "        <crazyattr4 attr1=\"true\">v18</crazyattr4>\n" +
                "        <crazyattr5 attr1=\"false\">v19</crazyattr5>\n" +
                "        <crazyattr6 attr1=\"\">v20</crazyattr6>\n" +
                "        <crazyattr7 attr1=\"\">v21</crazyattr7>\n" +
                "        <crazyattr9>\n" +
                "            <attr1>\n" +
                "                <key>4</key>\n" +
                "            </attr1>\n" +
                "            <crazyattr9>v23</crazyattr9>\n" +
                "        </crazyattr9>\n" +
                "        <crazyattr10 attr1=\"\">v24</crazyattr10>\n" +
                "        <crazyattr11>\n" +
                "            <attr1>better</attr1>\n" +
                "            <crazyattr11>v25</crazyattr11>\n" +
                "        </crazyattr11>\n" +
                "        <crazyattr12>\n" +
                "            <attr1>\n" +
                "                <element></element>\n" +
                "            </attr1>\n" +
                "            <crazyattr12>v26</crazyattr12>\n" +
                "        </crazyattr12>\n" +
                "    </transaction>\n" +
                "    <meta>\n" +
                "        <version>0.01</version>\n" +
                "    </meta>\n" +
                "</root>"
        );


        allTests.put(
            "<root><transaction><id>6753327</id><number region='Russia'>8-900-000-000</number><special1>false</special1><special2>true</special2><empty1 /><empty2></empty2><array1><element /><element /></array1><array2><element></element><element /><element>123</element><element>123.456</element><element><key1>value1</key1><key2 attr=\"value2\">value3</key2></element><element attr2='value4'>value5</element><element><attr3>value4</attr3><elem>value5</elem></element><element><deep deepattr=\"deepvalue\"><element>1</element><element>2</element><element>3</element></deep></element></array2><inner1><inner2><inner3><key1>value1</key1><key2>value2</key2></inner3></inner2></inner1><inner4><inner4>value3</inner4></inner4><inner5><attr1>123.456</attr1><inner4>value4</inner4></inner5><inner6 attr2=\"789.321\">value5</inner6><inner7>value6</inner7><inner8><attr3>value7</attr3></inner8><inner9><attr4>value8</attr4><inner9>value9</inner9><something>value10</something></inner9><inner10 attr5='' /><inner11 attr11=\"value11\"><inner12 attr12=\"value12\"><inner13 attr13=\"value13\"><inner14>v14</inner14></inner13></inner12></inner11><inner15></inner15><inner16><somekey>keyvalue</somekey><inner16>notnull</inner16></inner16><crazyattr1 attr1='123'>v15</crazyattr1><crazyattr2 attr1=\"123.456\">v16</crazyattr2><crazyattr3 attr1=''>v17</crazyattr3><crazyattr9><attr1><key>4</key></attr1><crazyattr9>v23</crazyattr9></crazyattr9></transaction><meta><version>0.01</version></meta></root>",


            "{\n" +
                "    \"root\": {" +
                "        \"transaction\": {\n" +
                "            \"id\": \"6753327\",\n" +
                "            \"number\": {\n" +
                "                \"@region\": \"Russia\",\n" +
                "                \"#number\": \"8-900-000-000\"\n" +
                "            },\n" +
                "            \"special1\": \"false\",\n" +
                "            \"special2\": \"true\",\n" +
                "            \"empty1\": null,\n" +
                "            \"empty2\": \"\",\n" +
                "            \"array1\": [\n" +
                "                null, null\n" +
                "            ],\n" +
                "            \"array2\": [\n" +
                "                \"\",\n" +
                "                null,\n" +
                "                \"123\",\n" +
                "                \"123.456\",\n" +
                "                {\n" +
                "                    \"key1\": \"value1\",\n" +
                "                    \"key2\": {\n" +
                "                        \"@attr\": \"value2\",\n" +
                "                        \"#key2\": \"value3\"\n" +
                "                    }\n" +
                "                },\n" +
                "                {\n" +
                "                    \"@attr2\": \"value4\",\n" +
                "                    \"#element\": \"value5\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"attr3\": \"value4\",\n" +
                "                    \"elem\": \"value5\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"deep\": {\n" +
                "                        \"@deepattr\": \"deepvalue\",\n" +
                "                        \"#deep\": [\n" +
                "                            \"1\",\n" +
                "                            \"2\",\n" +
                "                            \"3\"\n" +
                "                        ]\n" +
                "                    }\n" +
                "                }\n" +
                "            ],\n" +
                "            \"inner1\": {\n" +
                "                \"inner2\": {\n" +
                "                    \"inner3\": {\n" +
                "                        \"key1\": \"value1\",\n" +
                "                        \"key2\": \"value2\"\n" +
                "                    }\n" +
                "                }\n" +
                "            },\n" +
                "            \"inner4\": {\n" +
                "                \"inner4\": \"value3\"\n" +
                "            },\n" +
                "            \"inner5\": {\n" +
                "                \"attr1\": \"123.456\",\n" +
                "                \"inner4\": \"value4\"\n" +
                "            },\n" +
                "            \"inner6\": {\n" +
                "                \"@attr2\": \"789.321\",\n" +
                "                \"#inner6\": \"value5\"\n" +
                "            },\n" +
                "            \"inner7\": \"value6\",\n" +
                "            \"inner8\": {\n" +
                "                \"attr3\": \"value7\"\n" +
                "            },\n" +
                "            \"inner9\": {\n" +
                "                \"attr4\": \"value8\",\n" +
                "                \"inner9\": \"value9\",\n" +
                "                \"something\": \"value10\"\n" +
                "            },\n" +
                "            \"inner10\": {\n" +
                "                \"@attr5\": \"\",\n" +
                "                \"#inner10\": null\n" +
                "            },\n" +
                "            \"inner11\": {\n" +
                "                \"@attr11\": \"value11\",\n" +
                "                \"#inner11\": {\n" +
                "                    \"inner12\": {\n" +
                "                        \"@attr12\": \"value12\",\n" +
                "                        \"#inner12\": {\n" +
                "                            \"inner13\": {\n" +
                "                                \"@attr13\": \"value13\",\n" +
                "                                \"#inner13\": {\n" +
                "                                    \"inner14\": \"v14\"\n" +
                "                                }\n" +
                "                            }\n" +
                "                        }\n" +
                "                    }\n" +
                "                }\n" +
                "            },\n" +
                "            \"inner15\": \"\",\n" +
                "            \"inner16\": {\n" +
                "                \"somekey\": \"keyvalue\",\n" +
                "                \"inner16\": \"notnull\"\n" +
                "            },\n" +
                "            \"crazyattr1\": {\n" +
                "                \"@attr1\": \"123\",\n" +
                "                \"#crazyattr1\": \"v15\"\n" +
                "            },\n" +
                "            \"crazyattr2\": {\n" +
                "                \"@attr1\": \"123.456\",\n" +
                "                \"#crazyattr2\": \"v16\"\n" +
                "            },\n" +
                "            \"crazyattr3\": {\n" +
                "                \"@attr1\": \"\",\n" +
                "                \"#crazyattr3\": \"v17\"\n" +
                "            },\n" +
                "            \"crazyattr9\": {\n" +
                "                \"attr1\": {\n" +
                "                    \"key\": \"4\"\n" +
                "                },\n" +
                "                \"crazyattr9\": \"v23\"\n" +
                "            }\n" +
                "        },\n" +
                "        \"meta\": {\n" +
                "            \"version\": \"0.01\"\n" +
                "        }\n" +
                "    }\n" +
                "}"
        );
    }

    @Override
    public List<TestCase<Clue>> generate() {

        List<TestCase<Clue>> tests = new ArrayList<>();

        for (String input : allTests.keySet()) {
            String answer = allTests.get(input);

            TestCase<Clue> test = new TestCase<>();
            test.addFile("test.txt", input);
            test.setAttach(new Clue(answer, input));

            tests.add(test);
        }

        return tests;
    }

    @Override
    public CheckResult check(String reply, Clue clue) {

        String user = reply.strip();
        String answer = clue.answer.strip();

        if (user.length() == 0) {
            return new CheckResult(false,
                "Your output is empty line.");
        }

        CheckResult result;

        if (user.charAt(0) != '<' && user.charAt(0) != '{') {
            return new CheckResult(false,
                "Your first symbol is wrong - " +
                    " should be '{' or '<'");
        }

        try {
            if (user.charAt(0) == '<' && answer.charAt(0) == '<') {
                result = isEqualXMLs(user, answer);
            } else if (user.charAt(0) == '{' && answer.charAt(0) == '{') {
                result = isEqualJSONs(user, answer);
            } else {
                return new CheckResult(false,
                    "Your first symbol is wrong - " +
                        "'{' instead of '<' or vice versa" + "\n\n" + user + "\n\n" + answer);
            }
        } catch (Exception ex) {
            return new CheckResult(false,
                "Can't check the output - invalid XML or JSON");
        }

        //Tack on the input to a fail message for reference
        if (!result.isCorrect()) {
            result = new CheckResult(false, result.getFeedback() +
                    "\n\nThe input was: \n" + clue.input);
        }

        return result;
    }

    public static Element stringToXML(String str) throws Exception {
        DocumentBuilderFactory factory =
            DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        ByteArrayInputStream input = new ByteArrayInputStream(
            str.getBytes("UTF-8"));

        Document document = builder.parse(input);

        return document.getDocumentElement();
    }


    public static CheckResult isEqualXMLs(String s1, String s2) throws Exception {
        Element elem1 = stringToXML(s1);
        Element elem2 = stringToXML(s2);

        CheckResult result = isEqualXMLElements(elem1, elem2);

        if (!result.isCorrect()) {
            return result;
        } else {
            return isEqualXMLElements(elem2, elem1);
        }
    }

    public static CheckResult isEqualXMLElements(Element e1, Element e2) {
        // test name
        if (!e1.getNodeName().equals(e2.getNodeName())) {
            return new CheckResult(false,
                    "In XML: element name is incorrect.\n"
                            + e1.getNodeName() + " versus " + e2.getNodeName());
        }

        // test attributes
        NamedNodeMap attributes = e1.getAttributes();
        for (int i = 0; i < attributes.getLength(); i++) {
            Attr attr = (Attr) attributes.item(i);

            String name = attr.getName();

            if (!e2.hasAttribute(name)) {
                return new CheckResult(false,
                    "In XML: element doesn't have " +
                        "an attribute or has an excess attribute. \nAttribute Name: " + name + "\nElement:\n" + e2.toString()
                        );
            }

            if (!attr.getValue().equals(e2.getAttribute(name))) {
                return new CheckResult(false,
                        "In XML: element has an attribute " +
                                "but their values don't match.\nAttribute name: "
                                + name + "\nValue "
                                + attr.getValue() + " versus " + e2.getAttribute(name));
            }
        }

        // test child nodes
        if (e1.hasChildNodes() != e2.hasChildNodes()) {
            return new CheckResult(false,
                    "In XML: element doesn't have needed " +
                            "child nodes or has excess child nodes.\nElement name: " +
                            e1.getNodeName());
        }

        if (!e1.hasChildNodes()) {
            return CheckResult.correct();
        }

        NodeList childs1 = e1.getChildNodes();
        NodeList childs2 = e2.getChildNodes();

        List<Element> filteredChilds1 = new ArrayList<>();
        List<Element> filteredChilds2 = new ArrayList<>();

        for (int i = 0; i < childs1.getLength(); i++) {
            Object item = childs1.item(i);
            if (item instanceof Element) {
                filteredChilds1.add((Element) childs1.item(i));
            }
        }

        for (int i = 0; i < childs2.getLength(); i++) {
            Object item = childs2.item(i);
            if (item instanceof Element) {
                filteredChilds2.add((Element) childs2.item(i));
            }
        }

        if (filteredChilds1.size() != filteredChilds2.size()) {
            return new CheckResult(false,
                    "In XML: element doesn't have needed " +
                            "child nodes or has excess child nodes.\nElement name: " +
                            e1.getNodeName() + "\nNumber of nodes: " +
                            filteredChilds1.size() + " versus " + filteredChilds2.size());
        }

        for (int i = 0; i < filteredChilds1.size(); i++) {

            Element elem1 = filteredChilds1.get(i);
            Element elem2 = filteredChilds2.get(i);

            CheckResult result = isEqualXMLElements(elem1, elem2);
            if (!result.isCorrect()) {
                return result;
            }
        }

        return CheckResult.correct();
    }

    public static JsonElement stringToJSON(String str) {
        return new JsonParser().parse(str);
    }


    public static CheckResult isEqualJSONs(String s1, String s2) {
        JsonElement elem1 = stringToJSON(s1);
        JsonElement elem2 = stringToJSON(s2);

        CheckResult result = isEqualJSONElements(elem1, elem2);

        if (!result.isCorrect()) {
            return result;
        } else {
            return isEqualJSONElements(elem2, elem1);
        }
    }

    public static CheckResult isEqualJSONElements(JsonElement e1, JsonElement e2) {

        // check for null
        if (e1.isJsonNull() != e2.isJsonNull()) {
            return new CheckResult(false,
                "In JSON: expected null but found something else " +
                    "(or vice versa)\n"+
                        e1.toString() + "\nversus\n" + e2.toString());
        }
        if (e1.isJsonNull()) {
            return CheckResult.correct();
        }


        // check for primitives
        if (e1.isJsonPrimitive() != e2.isJsonPrimitive()) {
            // number and boolean are also may be expected but
            // after converting from XML there can be only strings
            return new CheckResult(false,
                "In JSON: expected string " +
                    "but found something else (or vice versa)\n"+
                        e1.toString() + "\nversus\n" + e2.toString());
        }
        if (e1.isJsonPrimitive()) {
            JsonPrimitive prim1 = e1.getAsJsonPrimitive();
            JsonPrimitive prim2 = e2.getAsJsonPrimitive();
            return compareJSONPrimitives(prim1, prim2);
        }


        // check for arrays
        if (e1.isJsonArray() != e2.isJsonArray()) {
            return new CheckResult(false,
                "In JSON: expected array " +
                    "but found something else (or vice versa)\n"+
                        e1.toString() + "\nversus\n" + e2.toString());
        }
        if (e1.isJsonArray()) {
            JsonArray arr1 = e1.getAsJsonArray();
            JsonArray arr2 = e2.getAsJsonArray();
            return compareJSONArrays(arr1, arr2);
        }


        // check for objects
        if (e1.isJsonObject() != e2.isJsonObject()) {
            return new CheckResult(false,
                "In JSON: expected object " +
                    "but found something else (or vice versa)\n"+
                        e1.toString() + "\nversus\n" + e2.toString());
        }
        if (e1.isJsonObject()) {
            JsonObject obj1 = e1.getAsJsonObject();
            JsonObject obj2 = e2.getAsJsonObject();
            return compareJSONObjects(obj1, obj2);
        }

        return CheckResult.correct();
    }


    public static CheckResult compareJSONPrimitives(JsonPrimitive prim1,
                                                    JsonPrimitive prim2) {

        if (prim1.isBoolean() && prim2.isBoolean()) {
            return new CheckResult(
                prim1.getAsBoolean() == prim2.getAsBoolean(),
                "In JSON: two boolean values don't match\n" +
                        prim1.toString() + "\nversus\n" + prim2.toString());
        }
        if (prim1.isNumber() && prim2.isNumber()) {
            BigDecimal num1 = prim1.getAsBigDecimal();
            BigDecimal num2 = prim2.getAsBigDecimal();
            return new CheckResult(num1.equals(num2),
                "In JSON: two number values don't match\n" +
                        prim1.toString() + "\nversus\n" + prim2.toString());
        }
        if (prim1.isString() && prim2.isString()) {
            String num1 = prim1.getAsString();
            String num2 = prim2.getAsString();
            return new CheckResult(num1.equals(num2),
                "In JSON: two string values don't match\n" +
                        prim1.toString() + "\nversus\n" + prim2.toString());
        }


        if (prim1.isString() && prim2.isNumber() ||
            prim1.isNumber() && prim2.isString()) {

            return new CheckResult(false,
                "In JSON: expected string value but " +
                    "found number (or vice versa)\n" +
                        prim1.toString() + "\nversus\n" + prim2.toString());
        }
        if (prim1.isString() && prim2.isBoolean() ||
            prim1.isBoolean() && prim2.isString()) {

            return new CheckResult(false,
                "In JSON: expected string value but " +
                    "found boolean (or vice versa)\n" +
                        prim1.toString() + "\nversus\n" + prim2.toString());
        }
        if (prim1.isNumber() && prim2.isBoolean() ||
            prim1.isBoolean() && prim2.isNumber()) {

            return new CheckResult(false,
                "In JSON: expected number value but " +
                    "found boolean (or vice versa)\n" +
                        prim1.toString() + "\nversus\n" + prim2.toString());
        }

        return CheckResult.correct();
    }

    public static CheckResult compareJSONArrays(JsonArray arr1, JsonArray arr2) {
        if (arr1.size() != arr2.size()) {
            return new CheckResult(false,
                "In JSON: array size is incorrect\n" +
                    arr1.size() + " versus " + arr2.size());
        }

        for (int i = 0; i < arr1.size(); i++) {
            JsonElement elem1 = arr1.get(i);
            JsonElement elem2 = arr2.get(i);

            CheckResult result = isEqualJSONElements(elem1, elem2);
            if (!result.isCorrect()) {
                return result;
            }
        }

        return CheckResult.correct();
    }

    public static CheckResult compareJSONObjects(JsonObject obj1, JsonObject obj2) {

        for (String key : obj1.keySet()) {
            if (!obj2.has(key)) {
                return new CheckResult(false,
                        "In JSON: object doesn't have " +
                                "needed key or has an excess key.\nTriggered on key: " + key +
                                "\nKeyset 1: " + obj1.keySet().toString() + "\nVersus Keyset 2: " + obj2.keySet().toString());
            }

            JsonElement value1 = obj1.get(key);
            JsonElement value2 = obj2.get(key);

            CheckResult result = isEqualJSONElements(value1, value2);
            if (!result.isCorrect()) {
                return result;
            }
        }

        return CheckResult.correct();
    }
}
