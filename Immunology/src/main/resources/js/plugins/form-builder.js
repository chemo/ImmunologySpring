﻿var container;
var formStructure = "";
var dropDownIdArr = [];
var formObj = [];

constants = {
		date: "Дата створення",	
		addition: "Додаткова інформація"
};

	



function renderForm(patientId, form) {
	container = form;
	$.ajax({
		type : "get",
		url : "/patients/" + patientId + "/medical_card",
		dataType : "json",
		success : function(response) {
			console.log("Start");
			console.log(response);
			
			formObj = response;
			renderMedicalForm(response);
			$(form).html(formStructure);
//			renderDropDownStyle();
			prepareForm(formObj);
		},
		error: function (request, status, error) {
			alert(error);
	    }

	});
};
function renderAnamnesticData(response,container){
	formStructure="";
	
	$(form.panels).each(function(index, panel) {
		renderPanel(panel, index);
		});
	formStructure +='<fieldset class= "panel-fieldset" style="text-align: center; "><button type="button" id="save_anamnestic_data_button"  class="btn btn-primary">Save Anamnestic Data</button></fieldset>';
	formStructure += '</form>';
	$(container).html(formStructure);
	prepareForm(formObj);
	
	$("#save_anamnestic_data_button").click(function(){
		
	});
	
}

function sendForm(){
	console.log(formObj);
	$.ajax({
	  type:"POST", 
      url:"/patients/medical_card/update",
      data: JSON.stringify( formObj),
      contentType: "application/json; charset=utf-8",
      dataType: "json",
       //Stringified Json Object
      //async: false,    //Cross-domain requests and dataType: "jsonp" requests do not support synchronous operation
     // cache: false,    //This will force requested pages not to be cached by the browser  
      //processData:false, //To avoid making query String instead of JSON
      success: function(resposeJsonObject){
    	  console.log(resposeJsonObject);
      },
	
	error: function (request, status, error) {
		alert(error);
    }});
}

	// Create structure of medical form
function renderMedicalForm(form){
	formStructure = '<form class="form-horizontal">';
		if(form.objectType == "MedicalCardForm"){
			renderMedcardFields(form);
		}
		$(form.panels).each(function(index, panel) {
		renderPanel(panel, index);
		});
	formStructure +='<fieldset class= "panel-fieldset" style="text-align: center; "><button type="button" onClick="sendForm()" class="btn btn-primary">Save</button></fieldset>';
	formStructure += '</form>';
}

	// Render Medical form fields( Date & Addition. Inf.)
function renderMedcardFields(form){
	
	formStructure += '<fieldset class= "panel-fieldset">';
	formStructure += 	'<div class = "col-sm-3 panel-title-block" >';
	formStructure +=			'<div class = "com-sm-7 med_panel_title_div disable" >' + constants.date + '</div>';
	formStructure +=		'</div>';
	formStructure +=		'<div class = "col-sm-9">';
	formStructure += 		'<div class="col-sm-5">';
	formStructure += 			'<input type="text" disabled value="'
								+ form.creationDate + '" class="form-control med_panel_left_input">';
	formStructure +=		'</div>';
	formStructure += 		'<div class="col-sm-7"></div></div>';
	
	formStructure += 	'<div class = "col-sm-3 panel-title-block">';
	formStructure +=			'<div class = "com-sm-7 med_panel_title_div disable" >' + constants.addition + '</div>';
	formStructure +=		'</div>';
	formStructure +=		'<div class = "col-sm-9">';
	formStructure += 			'<div class="col-sm-5">';
	formStructure += 				'<textArea id="additionInfo" onchange="storeMedCardDetailValue()" class="form-control med_panel_left_input">'
									+ form.additionalInfo + '</textArea>';
	formStructure += 			'</div>';
	formStructure += 	'</div>';
	formStructure += '</fieldset>';
}
	//render panel(title & made place for fields)
function renderPanel(panel,panelIndex) {
	formStructure += '<fieldset class= "panel-fieldset">';
	formStructure += 	'<div class = "col-sm-3 groupe-padding" >';
	formStructure +=		'<div class = "com-sm-7 med_panel_title_div disable" id="'+"panel_"+panelIndex +'"><h4>' + panel.name + '</h4></div>';
	formStructure +=	'</div>';
	formStructure +=	'<div class = "col-sm-9 group-panel">';
			$(panel.elements).each(function(elementIndex, element) {
				renderElement(element, panelIndex, elementIndex);
			});
	formStructure += 	'</div>';
	formStructure += '</fieldset>';
};

function renderElement(element, panelIndex, elementIndex) {
	renderCheckBox(element, panelIndex, elementIndex , null );
	
	switch (element.objectType) {
	case 'TextBox':
		formStructure += '</div>';
		renderTextBox(element, panelIndex, elementIndex, null);
		break;
	case 'Panel':
		renderSubPanel(element, panelIndex, elementIndex);
		break;
	case 'DropDown':
		renderDropDown(element,false, panelIndex, elementIndex, null);
		break;
	}
};


function renderTextBox(element,panelIndex,elementIndex, subElementIndex) {
	formStructure += '<div class="col-sm-7">';
	formStructure +=	'<input type="text" class="form-control" id="panel_'+panelIndex+'_element_'+elementIndex+'_'+subElementIndex+'" value="'
						+ element.text + '" onchange="storeValue('+panelIndex+','+elementIndex+','+subElementIndex+')" >';
	formStructure +='</div>';
};

function renderCheckBox(element, panelIndex, elementIndex,subElementIndex) {
	formStructure += '<div class="col-sm-5">';
	formStructure +=	'<div class="checkbox"  ><label><input onchange="checkboxClick('+panelIndex+','+elementIndex+','+subElementIndex+');" type="checkbox" ';
	formStructure += 			element.checked ? 'checked' : '';
	formStructure +=		' >';
	formStructure +=		 element.name;
	formStructure += 		'<i class="fa fa-square-o"></i></label>';
	formStructure += 	'</div>';
};
function renderSubPanelCheckBox(element, panelIndex, elementIndex,subElementIndex) {
	formStructure += '<div class="col-sm-5" style="padding-left: 38px;">';
	formStructure +=	'<div class="checkbox"  ><label><input onchange="checkboxClick('+panelIndex+','+elementIndex+','+subElementIndex+');" type="checkbox" ';
	formStructure += 			element.checked ? 'checked' : '';
	formStructure +=		' >';
	formStructure +=		 element.name;
	formStructure += 		'<i class="fa fa-square-o"></i></label>';
	formStructure += '</div>';
};


//SubElement is boolean variable whitch inform is the dropdown under the sumpanel;
function renderDropDown(element,subElement, panelIndex, elementIndex, subElementIndex) {
		//Cut "</div>";
	if(subElement){
		formStructure = formStructure.substring(0, formStructure.length - 34);
	}else {
		formStructure = formStructure.substring(0, formStructure.length - 6);
	}
		//Collect all id's for render DropDown styles;
	dropDownIdArr.push("panel_"+panelIndex+"_dropDownElement_"+elementIndex+"_"+ subElementIndex);
	formStructure += '<div class="col-sm-12">';
	formStructure +=	'<select class="form-control"  id="'
						+ 'panel_'+panelIndex+'_dropDownElement_'+elementIndex+'_'+ subElementIndex + '" onchange="storeDropDownValue('+panelIndex+','+elementIndex+','+subElementIndex+')" >';
			$(Object.keys(element.values)).each(function(key, optionElement) {
				if(key == element.choosed){
					formStructure += 		'<option value="' + key + '" selected = "selected">' + optionElement + '</option>';
				}else {
					formStructure += 		'<option value="' + key + '">' + optionElement + '</option>';
				}
						});
	formStructure += 	'</select>';
	formStructure +='</div>';
	formStructure +='</div></div>';
	
	if(subElement){
		formStructure = formStructure.substring(0, formStructure.length - 6);
	}
		//render dropdown right textbox field;
	formStructure += '<div class="col-sm-7" style="margin-bottom: 5px;">';
	formStructure +=	'<input type="text" class="form-control dropdown_text" id="panel_'+panelIndex+'_element_'+elementIndex+'_'+subElementIndex+'" value="'
	+ element.text + '" onchange="storeValue('+panelIndex+','+elementIndex+','+subElementIndex+')" >';
	formStructure +='</div>';
}
function renderSubPanel(subpanel, panelIndex, elementIndex){
	formStructure += '</div>';
	formStructure += '<div class="col-sm-9"></div>';
		$(subpanel.elements).each(function(subElementIndex,element) {
			renderSubElement(element, panelIndex, elementIndex, subElementIndex);
		});

}
//finished field and made marging for subelements;
function renderSubElement(element, panelIndex, elementIndex, subElementIndex) {
	
	renderSubPanelCheckBox(element, panelIndex, elementIndex, subElementIndex);
	formStructure += '</div>';
	formStructure +='<div class="col-sm-7"></div>'; 
		switch (element.objectType) {
		case 'TextBox':
			renderTextBox(element, panelIndex, elementIndex, subElementIndex);
			break;
		case 'DropDown':
			renderDropDown(element, true, panelIndex, elementIndex, subElementIndex);
			break;
		}
};
function renderDropDownStyle(element) {

	var i;
	for (i = 0; i < dropDownIdArr.length; ++i) {
		$("#"+dropDownIdArr[i]).select2();
	}

}
