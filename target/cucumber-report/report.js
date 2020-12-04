$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("CarTaxCheck.feature");
formatter.feature({
  "line": 1,
  "name": "Validate vehicle registration details",
  "description": "",
  "id": "validate-vehicle-registration-details",
  "keyword": "Feature"
});
formatter.before({
  "duration": 3342096048,
  "status": "passed"
});
formatter.scenario({
  "line": 4,
  "name": "vehicle registration details",
  "description": "",
  "id": "validate-vehicle-registration-details;vehicle-registration-details",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "line": 5,
  "name": "read and extract vehicle registration numbers from car input file",
  "keyword": "Given "
});
formatter.step({
  "line": 6,
  "name": "I am on car tax check page",
  "keyword": "And "
});
formatter.step({
  "line": 7,
  "name": "I enter each registration and extract the vehicle details",
  "keyword": "When "
});
formatter.step({
  "line": 8,
  "name": "compare vehicle details with output file",
  "keyword": "Then "
});
formatter.match({
  "location": "VehicleDetailsSteps.readAndExtractVehicleRegistrationNumbersFromCarInputFile()"
});
formatter.result({
  "duration": 230305802,
  "status": "passed"
});
formatter.match({
  "location": "VehicleDetailsSteps.iAmOnCarTaxCheckPage()"
});
formatter.result({
  "duration": 779595402,
  "status": "passed"
});
formatter.match({
  "location": "VehicleDetailsSteps.iEnterEachRegistrationAndExtractTheVehicleDetails()"
});
formatter.result({
  "duration": 6957705913,
  "status": "passed"
});
formatter.match({
  "location": "VehicleDetailsSteps.compareVehicleDetailsWithOutputFile()"
});
formatter.result({
  "duration": 5372818,
  "status": "passed"
});
formatter.after({
  "duration": 70583,
  "status": "passed"
});
});