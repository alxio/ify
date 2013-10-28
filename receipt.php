<?php
	$features = array("Accelerometer", "Battery", "SMS", "Wifi");
	
	if(!isset($_GET["name"])){
	echo'
		<form name="input" action="receipt.php?" method="get">
		Receipt name:
		<input type="text" name="name" value="MyReceipt">
		<input type="submit" value="Generate">
		';
		foreach($features as $i => $feat){
			echo '<input type="checkbox" name="'.$feat.'">'.$feat.'</input>';
		}
		echo'
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
	public void requestParams(YParamList params) {
		// TODO Auto-generated method stub
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
