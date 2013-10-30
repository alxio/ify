<?php
	$features = array("Accelerometer", "Battery", "SMS", "Wifi");
	
	if(!isset($_GET["name"])){
	
	echo'
	<!DOCTYPE html>
	<html>
	<head>
	<script>
	var counter = 0;
	function addParam()
	{
		document.getElementById("params").innerHTML += \'Name:<input type="text" name="param\'
		+(++counter) +\'Name"></input>Type:<input type="text" name="param\'
		+(counter) +\'Type"></input>Default:<input type="text" name="param\'
		+(counter) +\'Value"></input><br>\';
	}
	</script>
	</head>
	<body>
		<form name="input" action="receipt.php?" method="get">
		<b>Receipt name:</b><br>
		<input type="text" name="name" value="MyReceipt"><br>
		<b>Features:</b><br>
		';
		foreach($features as $i => $feat){
			echo '<input type="checkbox" name="'.$feat.'">'.$feat.'</input><br>';
		}
		echo'
		<b>Params:</b><br>
		<div id="params">
		
		</div>
		<button type="button" onClick="addParam()">Add param</button><br>
		<input type="submit" value="Generate">
		</form>
	';
	}else{
	$name = $_GET["name"];
	
	header("Content-type: text/plain");
	//header("Content-Disposition: attachment; filename=\"$name.java\"");
	echo"
import pl.poznan.put.cs.ify.api.*;
import pl.poznan.put.cs.ify.api.params.*;
import pl.poznan.put.cs.ify.api.types.*;
import pl.poznan.put.cs.ify.api.features.*;

public class $name extends YReceipt {

	@Override
	public void handleEvent(YEvent event) {
";
	foreach($_GET as $feature => $val){
	if($val == 'on'){
		echo'
		if(event.getId() == Y.'.$feature.'){
			Y'.$feature.'Event e = (Y'.$feature.'Event) event;
			//TODO: Insert your code here
		}
		';
		}
	}
	echo"
	}

	@Override
	public void requestFeatures(YFeatureList features) {
";
	foreach($_GET as $feature => $val)
	if($val == 'on'){
		echo"		features.add(new Y.".$feature."Feature());\n";
	}
	echo"	}

	@Override
	public void requestParams(YParamList params) {";

	foreach($_GET as $id => $val)
	if(substr($id,0,5) == 'param'){
		$params[substr($id,5,1)][substr($id,6)] = $val;
	}
	foreach($params as $param){
		echo'
		params.add("'.$param["Name"].'", YParamType.'.$param["Type"].', "'.$param["Value"].'");';
	}
echo"
	}

	@Override
	public String getName() {
		//Generated code, don't change.
		return \"$name\";
	}

	@Override
	public YReceipt newInstance() {
		//Generated code, don't change.
		return new $name();
	}
}
";
}
?>
