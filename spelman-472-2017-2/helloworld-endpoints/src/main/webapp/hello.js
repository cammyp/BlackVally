/*
 * http://stackoverflow.com/questions/18260815/use-gapi-client-javascript-to-execute-my-custom-google-api
 * https://developers.google.com/appengine/docs/java/endpoints/consume_js
 * https://developers.google.com/api-client-library/javascript/reference/referencedocs#gapiclientload
 *
 */

/**
 * After the client library has loaded, this init() function is called.
 * The init() function loads the helloworldendpoints API.
 */

var companyGlobal;

function init() {
	
	// You need to pass the root path when you load your API
	// otherwise calls to execute the API run into a problem
	
	// rootpath will evaulate to either of these, depending on where the app is running:
	// //localhost:8080/_ah/api
	// //your-app-id/_ah/api

	var rootpath = "//" + window.location.host + "/_ah/api";
	
	// Load the helloworldendpoints API
	// If loading completes successfully, call loadCallback function
	gapi.client.load('helloworldendpoints', 'v1', loadCallback, rootpath);
}

/*
 * When helloworldendpoints API has loaded, this callback is called.
 * 
 * We need to wait until the helloworldendpoints API has loaded to
 * enable the actions for the buttons in index.html,
 * because the buttons call functions in the helloworldendpoints API
 */
function loadCallback () {	
	// Enable the button actions
	enableButtons ();
}

function enableButtons () {
	btn = document.getElementById("input_greet_by_name");
	btn.onclick=function(){greetByName()};
	
	// Update the button label now that the button is active
	btn.value="See Stats Now!";
	
	btn = document.getElementById("input_update_race");
	btn.onclick=function(){updateRace()};
}

/*
 * Execute a request to the sayHello() endpoints function
 */
function greetGenerically () {
	// Construct the request for the sayHello() function
	var request = gapi.client.helloworldendpoints.sayHello();
	
	// Execute the request.
	// On success, pass the response to sayHelloCallback()
	request.execute(sayHelloCallback);
}

/*
 * Execute a request to the sayHelloByName() endpoints function.
 * Illustrates calling an endpoints function that takes an argument.
 */

function updateRace() {
	
	//log.warning("made it here:)")
	var race = document.getElementById("race_id").value;
	var request = gapi.client.helloworldendpoints.sayHelloByName({'company': companyGlobal, 'race': race,});
	request.execute(updateRaceCallback);
	
}

function updateRaceCallback(response) {
	$('#results').html(response.message);
	$('#chartdiv').removeClass('hidden');
	displayChart(response);
}

function greetByName () {
	// Get the name from the name_field element
	//var race = document.getElementById("race_id").value;
	var company = document.getElementById("company_id").value;
	companyGlobal = company;
	race = "Black";
	
	// Call the sayHelloByName() function.
	// It takes one argument "name"
	// On success, pass the response to sayHelloCallback()
	//var request = gapi.client.helloworldendpoints.sayHelloByName({'race': race, 'company': company});
	var request = gapi.client.helloworldendpoints.sayHelloByName({'company': company, 'race': race,});
	request.execute(sayHelloCallback);
}

// Process the JSON response
// In this case, just show an alert dialog box
// displaying the value of the message field in the response
function sayHelloCallback (response) {
	$( ".hide" ).hide();
	$( ".show" ).show();
	$('#results').html(response.message);
	$('#title').html(response.title);
	$('#chartdiv').removeClass('hidden');
	displayChart(response);
	
}

function displayChart(response) {
	    
	    AmCharts.makeChart("chartdiv",
				{
					"type": "pie",
					"balloonText": "[[title]]<br><span style='font-size:14px'><b>[[value]]</b> ([[percents]]%)</span>",
					"outlineThickness": 1,
					"titleField": "job",
					"valueField": "count",
					"fontSize": 12,
					"theme": "light",
					"allLabels": [],
					"balloon": {},
					"titles": [],
					"dataProvider":
						[
						{
							"job": "Professionals",
							"count": response.proCount
						},
						{
							"job": "Administrative support",
							"count": response.adminCount
						},
						{
							"job": "Lead/Manager",
							"count": response.leadCount
						},
						{
							"job": "Operatives",
							"count": response.operativesCount
						},
						{
							"job": "Technicians",
							"count": response.techCount
						},
						{
							"job": "Craft workers",
							"count": response.craftCount
						},
						{
							"job": "Sales workers",
							"count": response.salesCount
						},
						{
							"job": "Service workers",
							"count": response.serviceCount
						},
						{
							"job": "Executive/Senior Manager",
							"count": response.execCount
						},
						{
							"job": "laborers and helpers",
							"count": response.laborCount
						} 
					] 
				} 
			); 

}




