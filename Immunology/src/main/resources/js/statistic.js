	var typeOfChart;
	function drawChartDateOfRegistration() {
		var jsonData = $.ajax({
			url : "/statistic/medical_cards/by_years",
			dataType : "json",
			async : false
		}).responseText;

		var data = new google.visualization.DataTable();

		data.addColumn({
			"type" : "number",
			"label" : "Роки"
		});
		data.addColumn({
			"type" : "number",
			"label" : "Кількість пацієнтів"
		});
		
		jsonData = sort(jsonData);
		
		data.addRows(jsonData);
		var options = {
			width : '50%',
			height : 350,
		};
		var dataView = new google.visualization.DataView(data);
		dataView.setColumns([ {
			calc : function(data, row) {
				return data.getFormattedValue(row, 0);
			},
			type : 'string'
		}, 1 ]);
		selectTypeOfChart('chart_dateofregistration', dataView, options);
	}

	function drawSyndromePatient() {
		var jsonData = $.ajax({
			url : "/statistic/syndrome/by_patient",
			dataType : "json",
			async : false
		}).responseText;

		var data = new google.visualization.DataTable();
		data.addColumn({
			"type" : "string",
			"label" : "Синдром"
		});
		data.addColumn({
			"type" : "number",
			"label" : "Кількість пацієнтів"
		});
				
		data.addRows($.parseJSON(jsonData));
		var options = {
			width : '50%',
			height : 350,
		};
		var dataView = new google.visualization.DataView(data);
		dataView.setColumns([ {
			calc : function(data, row) {
				return data.getFormattedValue(row, 0);
			},
			type : 'string'
		}, 1 ]);
		selectTypeOfChart('chart_syndrome_patient', dataView, options);
	}
	
	function drawPatientSex() {
		var jsonData = $.ajax({
			url : "/statistic/patient/by_sex",
			dataType : "json",
			async : false
		}).responseText;

		var data = new google.visualization.DataTable();
		data.addColumn({
			"type" : "string",
			"label" : "Стать"
		});
		data.addColumn({
			"type" : "number",
			"label" : "Кількість пацієнтів"
		});
				
		data.addRows($.parseJSON(jsonData));
		var options = {
			width : '50%',
			height : 350,
		};
		var dataView = new google.visualization.DataView(data);
		dataView.setColumns([ {
			calc : function(data, row) {
				return data.getFormattedValue(row, 0);
			},
			type : 'string'
		}, 1 ]);
		selectTypeOfChart('chart_patient_sex', dataView, options);
	}

	function selectTypeOfChart(nameOfDiv, dataView, options) {
		var pieChart = new google.visualization.PieChart(document
				.getElementById(nameOfDiv));

		var columnChart = new google.visualization.ColumnChart(document
				.getElementById(nameOfDiv));

		var lineChart = new google.visualization.LineChart(document
				.getElementById(nameOfDiv));

		if (typeOfChart.trim() == 'Кругова діаграма') {
			pieChart.draw(dataView, options);
		} else if (typeOfChart.trim() == 'Стовпчикова діаграма') {
			columnChart.draw(dataView, options);
		} else if (typeOfChart.trim() == 'Гістограма') {
			histogram.draw(dataView, options);
		} else if (typeOfChart.trim() == 'Графік') {
			lineChart.draw(dataView, options);
		}
	}

	function sort(jsonData) {
		jsonData = $.parseJSON(jsonData);
		jsonData.sort(function(a, b) {
			var a1 = a[1], b1 = b[1];
			if (a1 == b1)
				return 0;
			return a1 > b1 ? 1 : -1;
		});
		return jsonData;
	}

	function drawAll(){
		drawChartDateOfRegistration();
		drawSyndromePatient();
		drawPatientSex();
	}
	
	$("#select_chart_button").click(function() {
		typeOfChart = $('#typeofchart').val();
		google.load("visualization", "1", {
			packages : [ "corechart" ],
			callback: drawAll
		});
	});

	$(document).ready(function() {
		$("#tabs").tabs();

	});
	

