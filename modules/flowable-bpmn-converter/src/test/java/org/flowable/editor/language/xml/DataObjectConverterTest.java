/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.flowable.editor.language.xml;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.StartEvent;
import org.flowable.bpmn.model.SubProcess;
import org.flowable.bpmn.model.UserTask;
import org.flowable.bpmn.model.ValuedDataObject;
import org.flowable.editor.language.xml.util.BpmnXmlConverterTest;

/**
 * @see <a href="https://activiti.atlassian.net/browse/ACT-1847">https://activiti.atlassian.net/browse/ACT-1847</a>
 */
class DataObjectConverterTest {

    @BpmnXmlConverterTest("dataobjectmodel.bpmn")
    void validateModel(BpmnModel model) {
        FlowElement flowElement = model.getMainProcess().getFlowElement("start1");
        assertThat(flowElement).isInstanceOf(StartEvent.class);
        assertThat(flowElement.getId()).isEqualTo("start1");

        // verify the main process data objects
        List<ValuedDataObject> dataObjects = model.getMainProcess().getDataObjects();
        assertThat(dataObjects).hasSize(8);

        Map<String, ValuedDataObject> objectMap = new HashMap<>();
        for (ValuedDataObject valueObj : dataObjects) {
            objectMap.put(valueObj.getId(), valueObj);
        }

        ValuedDataObject dataObj = objectMap.get("dObj1");
        assertThat(dataObj.getId()).isEqualTo("dObj1");
        assertThat(dataObj.getName()).isEqualTo("StringTest");
        assertThat(dataObj.getItemSubjectRef().getStructureRef()).isEqualTo("xsd:string");

        dataObj = objectMap.get("dObj2");
        assertThat(dataObj.getName()).isEqualTo("BooleanTest");
        assertThat(dataObj.getItemSubjectRef().getStructureRef()).isEqualTo("xsd:boolean");

        dataObj = objectMap.get("dObj3");
        assertThat(dataObj.getName()).isEqualTo("DateTest");
        assertThat(dataObj.getItemSubjectRef().getStructureRef()).isEqualTo("xsd:datetime");

        dataObj = objectMap.get("dObj4");
        assertThat(dataObj.getName()).isEqualTo("DoubleTest");
        assertThat(dataObj.getItemSubjectRef().getStructureRef()).isEqualTo("xsd:double");

        dataObj = objectMap.get("dObj5");
        assertThat(dataObj.getName()).isEqualTo("IntegerTest");
        assertThat(dataObj.getItemSubjectRef().getStructureRef()).isEqualTo("xsd:int");

        dataObj = objectMap.get("dObj6");
        assertThat(dataObj.getName()).isEqualTo("LongTest");
        assertThat(dataObj.getItemSubjectRef().getStructureRef()).isEqualTo("xsd:long");

        dataObj = objectMap.get("dObjJson");
        assertThat(dataObj.getName()).isEqualTo("JsonTest");
        assertThat(dataObj.getItemSubjectRef().getStructureRef()).isEqualTo("xsd:json");

        dataObj = objectMap.get("dObjWithoutType");
        assertThat(dataObj.getName()).isEqualTo("UnknownTypeTest");
        assertThat(dataObj.getItemSubjectRef().getStructureRef()).isEqualTo("xsd:string");

        flowElement = model.getMainProcess().getFlowElement("userTask1");
        assertThat(flowElement).isInstanceOf(UserTask.class);
        assertThat(flowElement.getId()).isEqualTo("userTask1");
        UserTask userTask = (UserTask) flowElement;
        assertThat(userTask.getAssignee()).isEqualTo("kermit");

        flowElement = model.getMainProcess().getFlowElement("subprocess1");
        assertThat(flowElement).isInstanceOf(SubProcess.class);
        assertThat(flowElement.getId()).isEqualTo("subprocess1");
        SubProcess subProcess = (SubProcess) flowElement;
        assertThat(subProcess.getFlowElements()).hasSize(12);

        // verify the sub process data objects
        dataObjects = subProcess.getDataObjects();
        assertThat(dataObjects).hasSize(7);

        objectMap = new HashMap<>();
        for (ValuedDataObject valueObj : dataObjects) {
            objectMap.put(valueObj.getId(), valueObj);
        }

        dataObj = objectMap.get("dObj7");
        assertThat(dataObj.getId()).isEqualTo("dObj7");
        assertThat(dataObj.getName()).isEqualTo("StringSubTest");
        assertThat(dataObj.getItemSubjectRef().getStructureRef()).isEqualTo("xsd:string");

        dataObj = objectMap.get("dObj8");
        assertThat(dataObj.getId()).isEqualTo("dObj8");
        assertThat(dataObj.getName()).isEqualTo("BooleanSubTest");
        assertThat(dataObj.getItemSubjectRef().getStructureRef()).isEqualTo("xsd:boolean");

        dataObj = objectMap.get("dObj9");
        assertThat(dataObj.getId()).isEqualTo("dObj9");
        assertThat(dataObj.getName()).isEqualTo("DateSubTest");
        assertThat(dataObj.getItemSubjectRef().getStructureRef()).isEqualTo("xsd:datetime");

        dataObj = objectMap.get("dObj10");
        assertThat(dataObj.getId()).isEqualTo("dObj10");
        assertThat(dataObj.getName()).isEqualTo("DoubleSubTest");
        assertThat(dataObj.getItemSubjectRef().getStructureRef()).isEqualTo("xsd:double");

        dataObj = objectMap.get("dObj11");
        assertThat(dataObj.getId()).isEqualTo("dObj11");
        assertThat(dataObj.getName()).isEqualTo("IntegerSubTest");
        assertThat(dataObj.getItemSubjectRef().getStructureRef()).isEqualTo("xsd:int");

        dataObj = objectMap.get("dObj12");
        assertThat(dataObj.getId()).isEqualTo("dObj12");
        assertThat(dataObj.getName()).isEqualTo("LongSubTest");
        assertThat(dataObj.getItemSubjectRef().getStructureRef()).isEqualTo("xsd:long");

        dataObj = objectMap.get("dObjSubJson");
        assertThat(dataObj.getId()).isEqualTo("dObjSubJson");
        assertThat(dataObj.getName()).isEqualTo("JsonSubTest");
        assertThat(dataObj.getItemSubjectRef().getStructureRef()).isEqualTo("xsd:json");
    }
}
