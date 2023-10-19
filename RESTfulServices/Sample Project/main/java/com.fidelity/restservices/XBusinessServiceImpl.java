@Service
@Transactional
public class XYZBusinessServiceImpl implements XYZBusinessService {

    @Autowired
    private XYZDao xyzDao;

    @Override
    public List<XYZ> findAllXYZs() {
        List<XYZ> xyzs;

        try {
            xyzs = xyzDao.queryAllXYZs();
        }
        catch (Exception e) {
            String msg = "Error querying all XYZs in the XYZ database.";
            throw new XYZDatabaseException(msg, e);
        }

        return xyzs;
    }

    @Override
    public XYZ findXYZById(int id) {
        validateId(id);
        XYZ xyz = null;
        try {
            xyz = xyzDao.queryXYZById(id);
        }
        catch (Exception e) {
            String msg = String.format("Error querying For XYZ with id = %d in the Warehouse database.", id);
            throw new XYZDatabaseException(msg, e);
        }
        if(xyz == null) {
            String msg = String.format("Error querying For XYZ with id = %d in the Warehouse database.", id);
            throw new XYZDatabaseException(msg);
        }
        return xyz;
    }

    @Override
    public String findCaptainByXYZName(String name) {
        validateName(name);
        String captainName = null;
        try {
            captainName = xyzDao.queryCaptainByXYZName(name);
        }
        catch (Exception e) {
            String msg = String.format("Error querying For XYZ with name = %s in the XYZ database.", name);
            throw new XYZDatabaseException(msg, e);
        }
        return captainName;
    }

    private void validateId(int id) {
        if (id < 1) {
            throw new IllegalArgumentException("Invalid XYZ Id " + id);
        }
    }

    private void validateName(String name) {
        if (name == "" || name == null) {
            throw new IllegalArgumentException("Invalid XYZ Name " + name);
        }
    }
    
}