public interface XYZBusinessService 
{
	List<XYZ> findAllXYZs();
	XYZ findXYZById(int id);
	String findCaptainByXYZName(String name);

}
