@Repository
public class XYZDaoMyBatisImpl implements XYZDao 
{
	@Autowired
	private XYZMapper xyzMapper;
	
	@Override
	public List<XYZ> queryAllXYZs() {
		List<XYZ> xyzs = xyzMapper.getAllXYZs();
		
		return xyzs;
	}

	@Override
	public XYZ queryXYZById(int id) {
		return xyzMapper.getXYZById(id);
	}

}