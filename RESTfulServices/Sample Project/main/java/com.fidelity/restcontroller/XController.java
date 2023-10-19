@RestController
@RequestMapping("/xyzs")
public class XYZController {

	@Autowired
	private XYZBusinessService xyzBusinessService;

	@GetMapping(value="/ping", produces=MediaType.ALL_VALUE)
	public String ping() {
		return "XYZBusinessService is alive at " + LocalDateTime.now();
	}

	@GetMapping("")
	public ResponseEntity<List<XYZ>> getAllXYZs() {
		List<XYZ> xyzs;
		ResponseEntity<List<XYZ>> response;
		try {
			xyzs = xyzBusinessService.findAllXYZs();
		}
		catch(ServerErrorException ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (!xyzs.isEmpty()) {
			response = ResponseEntity.status(HttpStatus.OK).body(xyzs);
		}
		else {
			response = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return response;
	}
	
	// Get one xyz by ID
	@GetMapping("id/{id}")
	public ResponseEntity<XYZ> getXYZById(@PathVariable("id") int id) {
		XYZ xyz = null;
		ResponseEntity<XYZ> response;
		try {
			xyz = xyzBusinessService.findXYZById(id);
		}
		catch(IllegalArgumentException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		if (xyz != null) {
			response = ResponseEntity.status(HttpStatus.OK).body(xyz);
		}
		else {
			response = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return response;
	}

}