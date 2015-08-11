package j.jave.framework.components.resource.action;

import j.jave.framework.commons.ehcache.JEhcacheService;
import j.jave.framework.commons.ehcache.JEhcacheServiceAware;
import j.jave.framework.commons.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.framework.commons.io.memory.JSingleStaticMemoryCacheIO;
import j.jave.framework.commons.json.JJSON;
import j.jave.framework.commons.utils.JStringUtils;
import j.jave.framework.components.core.support.JSPActionSupport;
import j.jave.framework.components.login.model.Group;
import j.jave.framework.components.login.model.Role;
import j.jave.framework.components.login.service.GroupService;
import j.jave.framework.components.login.service.RoleService;
import j.jave.framework.components.resource.model.Resource;
import j.jave.framework.components.resource.model.ResourceExtend;
import j.jave.framework.components.resource.model.ResourceGroup;
import j.jave.framework.components.resource.model.ResourceRole;
import j.jave.framework.components.resource.service.ResourceExtendService;
import j.jave.framework.components.resource.service.ResourceGroupService;
import j.jave.framework.components.resource.service.ResourceRoleService;
import j.jave.framework.components.resource.service.ResourceService;
import j.jave.framework.components.resource.support.ResourceDetect;
import j.jave.framework.components.resource.support.ResourceInfo;
import j.jave.framework.components.support.ehcache.subhub.EhcacheService;
import j.jave.framework.components.web.subhub.resourcecached.ResourceCachedRefreshEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;



@Controller(value="resource.resourceaction")
@Scope(value=ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ResourceJSPAction extends JSPActionSupport implements JSingleStaticMemoryCacheIO<Map<String, List<ResourceInfo>>> ,JEhcacheServiceAware{
	
	@Autowired
	private ResourceService resourceService;
	
	@Autowired
	private ResourceExtendService resourceExtendService;
	
	@Autowired
	private ResourceGroupService resourceGroupService;
	
	@Autowired
	private ResourceRoleService resourceRoleService;
	
	private Resource resource=new Resource();
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private GroupService groupService;
	
	private static String moduleName;
	
	static{
		Controller controller=ResourceJSPAction.class.getAnnotation(Controller.class);
		if(controller!=null){
			moduleName=controller.value();
		}
	}
	
	
	
	public String toResourceManager(){
		
		List<Role> roles =roleService.getAllRoles(getServiceContext());
		setAttribute("roles", roles);
		
		List<Group> groups =groupService.getAllGroups(getServiceContext());
		setAttribute("groups", groups);
		
		List<String> moduleNames=new ArrayList<String>();
		Map<String, List<ResourceInfo>> resourceInfos=  get();
		for (Iterator<String> iterator = resourceInfos.keySet().iterator(); iterator.hasNext();) {
			String moduleName =  iterator.next();
			if(!moduleName.equals(ResourceJSPAction.moduleName)){
				moduleNames.add(moduleName);
			}
			
		}
		setAttribute("moduleNames", moduleNames);
		return "/WEB-INF/jsp/login/resource-manager.jsp";
	}
	
	
	public String getAllPathsOnModule(){
		String moduleName=getParameter("moduleName").trim();
		List<String> paths=new ArrayList<String>();
		Map<String, List<ResourceInfo>> resourceInfos=  get();
		List<ResourceInfo> resourceMaps= resourceInfos.get(moduleName);
		for (Iterator<ResourceInfo> iterator = resourceMaps.iterator(); iterator.hasNext();) {
			ResourceInfo resourceInfo =  iterator.next();
			paths.add(resourceInfo.getPath());
		}
		return JJSON.get().format(paths);
	}
	
	
	public String getExtensionOnResource(){
		
		String resourceId=getParameter("resourceId");
		String path=getParameter("path");

		if(JStringUtils.isNullOrEmpty(resourceId)){
			Resource resource=resourceService.getResourceByURL(getServiceContext(), path);
			if(resource!=null){
				resourceId=resource.getId();
			}
		}
		ResourceExtend resourceExtend=null;
		if(JStringUtils.isNotNullOrEmpty(resourceId)){
			resourceExtend=resourceExtendService.getResourceExtendOnResourceId(getServiceContext(), resourceId);
		}
		return JJSON.get().format(resourceExtend);
		
	}
	
	/**
	 * enhance the function of the resource. 
	 * such as , if supports URL cache. 
	 * @return
	 * @throws Exception 
	 */
	public String enhanceResource() throws Exception{
		String path=getParameter("path");
		String cached=getParameter("cached");
		
		ResourceExtend resourceExtend=new ResourceExtend();
		
		if(JStringUtils.isNotNullOrEmpty(cached)){
			resourceExtend.setCached(cached);
		}
		
		if(JStringUtils.isNotNullOrEmpty(path)){
			resourceExtend.setUrl(path);
		}
		Resource resource= resourceService.getResourceByURL(getServiceContext(), path);
		if(resource!=null){
			resourceExtend.setResourceId(resource.getId());
		}
		resourceExtend= resourceExtendService.enhanceResource(getServiceContext(), resourceExtend);
		JServiceHubDelegate.get().addDelayEvent(new ResourceCachedRefreshEvent(this));
		
		ResourceExtend ebResourceExtend=resourceExtendService.getById(getServiceContext(), resourceExtend.getId());
		
		return JJSON.get().format(ebResourceExtend); 
	}
	

	private static ResourceDetect resourceDetect=new ResourceDetect();
	
	private EhcacheService ehcacheService=null;
	
	@Override
	public EhcacheService getEhcacheService() {
		if(ehcacheService==null){
			ehcacheService=JServiceHubDelegate.get().getService(this, EhcacheService.class);
		}
		return ehcacheService;
	}
	
	@Override
	public void setEhcacheService(JEhcacheService ehcacheService) {
		this.ehcacheService=(EhcacheService) ehcacheService;
	}
	
	private static final String RESOURCE_CACHED_KEY="j.jave.framework.components.resource.resource.method.info";
	
	@Override
	public Map<String, List<ResourceInfo>> set() {
		Map<String, List<ResourceInfo>> resourceMaps=new ConcurrentHashMap<String, List<ResourceInfo>>();
		Map<Class<?>, List<ResourceInfo>> classesResourceInfos=  resourceDetect.detect().getClassMethodInfos();
		if(classesResourceInfos!=null&&!classesResourceInfos.isEmpty()){
			for (Iterator<Entry<Class<?>, List<ResourceInfo>>> iterator = classesResourceInfos.entrySet().iterator(); iterator.hasNext();) {
				Entry<Class<?>, List<ResourceInfo>> type =  iterator.next();
				String resourceModule="";
				Class<?> clazz=type.getKey();
				List<ResourceInfo> resourceInfos=type.getValue();
				if(resourceInfos!=null&&!resourceInfos.isEmpty()){
					resourceModule=resourceInfos.get(0).getControllerName();
				}
				else{
					resourceModule=clazz.getAnnotation(Controller.class).value();
				}
				resourceMaps.put(resourceModule, resourceInfos);
			}
		}
		getEhcacheService().put(RESOURCE_CACHED_KEY, resourceMaps);
		return resourceMaps;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, List<ResourceInfo>> get() {
		Object obj=getEhcacheService().get(RESOURCE_CACHED_KEY);
		if(obj==null){
			obj=set();
		}
		return (Map<String, List<ResourceInfo>>) obj;
	} 
	
	
	
	
	public String getResourceRole(){
		List<ResourceRole> resourceRoles=new ArrayList<ResourceRole>();
		String resourceId=getParameter("resourceId");
		String path=getParameter("path");
		if(JStringUtils.isNullOrEmpty(resourceId)){
			Resource resource=resourceService.getResourceByURL(getServiceContext(), path);
			if(resource!=null){
				resourceId=resource.getId();
			}
		}
		if(JStringUtils.isNotNullOrEmpty(resourceId)){
			resourceRoles= resourceRoleService.getResourceRolesByResourceId(getServiceContext(), resourceId);
		}
		
		return JJSON.get().format(resourceRoles); 
	}
	
	
	public String bingResourceOnRole() throws Exception{
		String resourceId=getParameter("resourceId");
		if(JStringUtils.isNotNullOrEmpty(resourceId)){
			resourceId=resourceId.trim();
		}
		String path=getParameter("path");
		if(JStringUtils.isNotNullOrEmpty(path)){
			path=path.trim();
		}
		String roleId=getParameter("roleId");
		if(JStringUtils.isNotNullOrEmpty(roleId)){
			roleId=roleId.trim();
		}
		
		if(JStringUtils.isNotNullOrEmpty(resourceId)){
			resourceRoleService.bingResourceRole(getServiceContext(), resourceId, roleId);
		}
		else if(JStringUtils.isNotNullOrEmpty(path)){
			ResourceRole resourceRole=resourceRoleService.bingResourcePathRole(getServiceContext(), path, roleId); 
			resourceId=resourceRole.getResourceId();
		}
		
		List<ResourceRole> resourceRoles= resourceRoleService.getResourceRolesByResourceId(getServiceContext(), resourceId);
		return JJSON.get().format(resourceRoles); 
	}
	
	public String unbingResourceOnRole() throws Exception{
		String resourceId=getParameter("resourceId");
		if(JStringUtils.isNotNullOrEmpty(resourceId)){
			resourceId=resourceId.trim();
		}
		String path=getParameter("path");
		if(JStringUtils.isNotNullOrEmpty(path)){
			path=path.trim();
		}
		String roleId=getParameter("roleId");
		if(JStringUtils.isNotNullOrEmpty(roleId)){
			roleId=roleId.trim();
		}
		if(JStringUtils.isNotNullOrEmpty(resourceId)){
			resourceRoleService.unbingResourceRole(getServiceContext(), resourceId, roleId);
		}
		else if(JStringUtils.isNotNullOrEmpty(path)){
			Resource resource=resourceService.getResourceByURL(getServiceContext(), path);
			resourceId=resource.getId();
			resourceRoleService.unbingResourceRole(getServiceContext(), resourceId, roleId);
		}
		
		List<ResourceRole> resourceRoles= resourceRoleService.getResourceRolesByResourceId(getServiceContext(), resourceId);
		return JJSON.get().format(resourceRoles); 
		
	}
	
	
	
	
	public String getResourceGroup(){
		
		String resourceId=getParameter("resourceId");
		if(JStringUtils.isNotNullOrEmpty(resourceId)){
			resourceId=resourceId.trim();
		}
		String path=getParameter("path");
		if(JStringUtils.isNotNullOrEmpty(path)){
			path=path.trim();
		}

		if(JStringUtils.isNullOrEmpty(resourceId)){
			Resource resource=resourceService.getResourceByURL(getServiceContext(), path);
			if(resource!=null){
				resourceId=resource.getId();
			}
		}
		List<ResourceGroup> resourceGroups=new ArrayList<ResourceGroup>();
		if(JStringUtils.isNotNullOrEmpty(resourceId)){
			resourceGroups= resourceGroupService.getResourceGroupsByResourceId(getServiceContext(), resourceId);
		}
		return JJSON.get().format(resourceGroups); 
	}
	
	
	public String bingResourceOnGroup() throws Exception{
		
		String resourceId=getParameter("resourceId");
		if(JStringUtils.isNotNullOrEmpty(resourceId)){
			resourceId=resourceId.trim();
		}
		String path=getParameter("path");
		if(JStringUtils.isNotNullOrEmpty(path)){
			path=path.trim();
		}
		String groupId=getParameter("groupId");
		if(JStringUtils.isNotNullOrEmpty(groupId)){
			groupId=groupId.trim();
		}
		
		
		if(JStringUtils.isNotNullOrEmpty(resourceId)){
			resourceGroupService.bingResourceGroup(getServiceContext(), resourceId, groupId);
		}
		else if(JStringUtils.isNotNullOrEmpty(path)){
			ResourceGroup resourceGroup=resourceGroupService.bingResourcePathGroup(getServiceContext(), path, groupId);
			resourceId=resourceGroup.getResourceId();
		}
		
		List<ResourceGroup> resourceGroups= resourceGroupService.getResourceGroupsByResourceId(getServiceContext(), resourceId);
		return JJSON.get().format(resourceGroups); 
	}
	
	public String unbingResourceOnGroup() throws Exception{
		String resourceId=getParameter("resourceId");
		if(JStringUtils.isNotNullOrEmpty(resourceId)){
			resourceId=resourceId.trim();
		}
		String path=getParameter("path");
		if(JStringUtils.isNotNullOrEmpty(path)){
			path=path.trim();
		}
		String groupId=getParameter("groupId");
		if(JStringUtils.isNotNullOrEmpty(groupId)){
			groupId=groupId.trim();
		}
		
		if(JStringUtils.isNotNullOrEmpty(resourceId)){
			resourceGroupService.unbingResourceGroup(getServiceContext(), resourceId, groupId);
		}
		else if(JStringUtils.isNotNullOrEmpty(path)){
			Resource resource=resourceService.getResourceByURL(getServiceContext(), path);
			resourceId=resource.getId();
			resourceGroupService.unbingResourceGroup(getServiceContext(), resourceId, groupId);
		}
		
		List<ResourceGroup> resourceGroups= resourceGroupService.getResourceGroupsByResourceId(getServiceContext(), resourceId);
		return JJSON.get().format(resourceGroups); 
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
