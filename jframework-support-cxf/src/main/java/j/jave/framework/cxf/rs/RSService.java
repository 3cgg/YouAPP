package j.jave.framework.cxf.rs;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;


@Path(value="/rsdemo")
@Produces
public interface RSService {
	
	@GET
	@Path(value="getRsDemoJSON/{id}/{name}")
	@Produces(value="application/json")
	@Consumes(value="application/json")
	RSDemo getRsDemoJSON(@PathParam(value="id") String id, @PathParam(value="name")String name);


	@GET
	@Path(value="getRsDemo/{id}/{name}")
	@Produces(value="application/xml")
	RSDemo getRsDemo(@PathParam(value="id") String id, @PathParam(value="name")String name);
	
	@POST
	@Path(value="saveRsDemo")
	@Produces(value="application/json")
	@Consumes(value="application/json")
	RSDemo saveRsDemo(RSDemo rsDemo);
	

	
}
