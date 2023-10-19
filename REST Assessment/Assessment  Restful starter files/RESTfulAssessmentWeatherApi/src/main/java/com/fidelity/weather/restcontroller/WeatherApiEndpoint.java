package com.fidelity.weather.restcontroller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * WeatherApiEndpoint is a stub REST service for testing applications that 
 * need to get weather forecast data. 
 * For real-time weather forecasts, see the US National Weather Service API
// * deployed at https://www.weather.gov/documentation/services-web-api
 * 
 * @author ROI Instructor Team
 */
@RestController
@RequestMapping("/")
public class WeatherApiEndpoint {
	@Autowired
	private Logger logger;
	
	@SuppressWarnings("serial")
	private final Map<String, WeatherForecast> forecastMap = new HashMap<>() {{
		put("21.3099,-157.8581", new WeatherForecast( // Honolulu
			21.3099,
			-157.8581,
			"F",
			83,
			71,
			"Sunny",
			"Sunny, with a high near 83. East wind around 12 mph."
			));
		put("37.7749,-122.4194", new WeatherForecast( // San Francisco
			37.7749,
			-122.4194,
			"F",
			60,
			52,
			"Foggy",
			"Foggy, with a high near 60. West wind around 4 mph."
			));
		put("32.7157,-117.1611", new WeatherForecast( // San Diego
			32.7157,
			-117.1611,
			"F",
			75,
			53,
			"Partly sunny",
			"Partly sunny, with a high near 75. Southwest wind 5 to 10 mph."
			));
		put("47.6062,-122.3321", new WeatherForecast( // Seattle
			47.6062,
			-122.3321,
			"F",
			65,
			49,
			"Cloudy",
			"Cloudy, with a high near 65. Northwest wind around 6 mph."
			));
		put("38.9072,-77.0369", new WeatherForecast( // Washington DC
			38.9072,
			-77.0369,
			"F",
			79,
			58,
			"Haze",
			"Haze and isolated rain showers. Partly sunny, with a high near 79."
			));
		put("31.5785,-84.1557", new WeatherForecast( // Albany GA
			31.5785,
			-84.1557,
			"F",
			91,
			70,
			"Slight chance showers and thunderstorms",
			"A slight chance of showers and thunderstorms after 5pm. High near 91."
			));
		put("42.6526,-73.7562", new WeatherForecast( // Albany NY
			42.6526,
			-73.7562,
			"F",
			70,
			49,
			"Haze",
			"Haze and a chance of showers and thunderstorms. Partly sunny, with a high near 70."
			));
		put("40.7128,-74.006", new WeatherForecast( // New York NY
			40.7128,
			-74.0060,
			"F",
			74,
			49,
			"Haze",
			"Haze and areas of smoke and a chance of showers and thunderstorms. High near 74."
			));
		put("53.3498,-6.2603", new WeatherForecast( // Dublin D
			53.3498,
			-6.2603,
			"F",
			62,
			51,
			"Clear",
			"Clear with periodic clouds. High 62."
			));
		put("53.274,-9.0513", new WeatherForecast( // Galway G
			53.274,
			-9.0513,
			"F",
			74,
			47,
			"Clear",
			"Mainly clear. High 74."
			));
		put("13.0827,80.2707", new WeatherForecast( // Chennai TN
			13.0827,
			80.2707,
			"F",
			98,
			84,
			"Partly clouldy.",
			"Partly cloudly. High 98."
			));
		put("28.6139,77.209", new WeatherForecast( // New Delhi DL
			28.6139,
			77.2090,
			"F",
			100,
			81,
			"Sunny",
			"Sunny. High 100."
			));
		put("38.914,121.6147", new WeatherForecast( // Dalian LN
			38.914,
			121.6147,
			"F",
			75,
			63,
			"Scattered thuderstorms",
			"Scattered thuderstorms. High 75."
			));
		put("39.9042,116.4074", new WeatherForecast( // Beijing HE 
			39.9042,
			116.4074,
			"F",
			79,
			65,
			"Partly cloudy",
			"Partly cloudy. High 79."
			));
	}};

	@GetMapping(value = "/forecast/{latLong}",
			    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<WeatherForecast> getForecast(@PathVariable String latLong) {
		logger.debug("getGridCoordsJson({})", latLong);

		ResponseEntity<WeatherForecast> response = ResponseEntity.badRequest().build();
		
		WeatherForecast forecast = forecastMap.get(latLong);

		if (forecast != null) {
			response = ResponseEntity.ok(forecast);
		}

		logger.debug("getGridCoordsJson({}), returning {}", latLong, response);
		return response;
	}
	
	@GetMapping(produces = MediaType.TEXT_HTML_VALUE)
	public String landingPage() {
		return """
			<html>
			<head>
				<style>
					html { background-color: powderblue; font-family: sans-serif;}
					table, th, td { border: 1px solid black; border-collapse: collapse; }
					th { background-color: powderblue; } 
					tr:nth-child(odd) { background-color: #ffffff; }
					th, td { padding-left: 10px; padding-right: 10px; }
					#region { width: 50px; white-space: normal; }
				</style>
			</head>
			<body>
				<h1>Weather API</h1>
				
				<p>This REST API is a test stub for applications that require weather forecasts.

				<h2>Request for a Forecast</h2>
				
				<p>A request for a weather forecast has the following format:</p>
				<p><code><font size="+1">GET http://localhost:8088/forecast/<i>latitude</i>,<i>longitude</i></font></code></p>

				<p>Sample request: <a href="http://localhost:8088/forecast/21.3099,-157.8581">
									<code><font size="+1">http://localhost:8088/forecast/21.3099,-157.8581</font></code></a>
				</p>

				<p>Here is a list of the recognized <code><i><font size="+1">latitude,longitude</font></i></code> pairs. 
				   Note that the <code><i><font size="+1">latitude,longitude</font></i></code> string must not contain whitespace:<p>

				<table>
					<thead>
						<tr><th>Latitude,Longitude</th><th>Country</th><th id="region">State/ Province/ Region</th><th>City</th></tr>
					</thead>
					<tbody>
						<tr><td><code><font size="+1">39.9042,116.4074</font></code></td><td>CN</td><td>HE</td><td>Beijing</td></tr>
						<tr><td><code><font size="+1">38.914,121.6147</font></code></td><td>CN</td><td>LN</td><td>Dalien</td></tr>
						<tr><td><code><font size="+1">53.3498,-6.2603</font></code></td><td>IE</td><td>D</td><td>Dublin</td></li>
						<tr><td><code><font size="+1">53.274,-9.0513</font></code></td><td>IE</td><td>G</td><td>Galway</td></tr>
						<tr><td><code><font size="+1">28.6139,77.209</font></code></td><td>IN</td><td>DL</td><td>New Delhi</td></tr>
						<tr><td><code><font size="+1">13.0827,80.2707</font></code></td><td>IN</td><td>TN</td><td>Chennai</td></tr>
						<tr><td><code><font size="+1">32.7157,-117.1611</font></code></td><td>US</td><td>CA</td><td>San Diego</td></tr>
						<tr><td><code><font size="+1">37.7749,-122.4194</font></code></td><td>US</td><td>CA</td><td>San Francisco</td></tr>
						<tr><td><code><font size="+1">38.9072,-77.0369</font></code></td><td>US</td><td>DC</td><td>Washington</td></tr>
						<tr><td><code><font size="+1">31.5785,-84.1557</font></code></td><td>US</td><td>GA</td><td>Albany</td></tr>
						<tr><td><code><font size="+1">21.3099,-157.8581</font></code></td><td>US</td><td>HI</td><td>Honolulu</td></tr>
						<tr><td><code><font size="+1">42.6526,-73.7562</font></code></td><td>US</td><td>NY</td><td>Albany</td></tr>
						<tr><td><code><font size="+1">40.7128,-74.006</font></code></td><td>US</td><td>NY</td><td>New York</td></tr>
						<tr><td><code><font size="+1">47.6062,-122.3321</font></code></td><td>US</td><td>WA</td><td>Seattle</td></tr>
					</tbody>
				</table>
			</body>
			</html>
		""";
	}
}
