package com.rummy.rest.resources;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.types.ObjectId;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import com.mongodb.BasicDBObject;
import com.rummy.domain.ProposedResource;
import com.rummy.domain.Registration;
import com.rummy.domain.RequestResources;
import com.rummy.domain.Requirement;
import com.rummy.domain.RequirementWithSideMapper;
import com.rummy.domain.Resource;
import com.rummy.domain.ResourceExperience;
import com.rummy.exception.RAException;
import com.rummy.mapper.ObjectMapper;
import com.rummy.mapper.ObjectMapper2;
import com.rummy.mapper.RequirementMapper;
import com.rummy.mapper.ResourceMapper;
import com.rummy.services.ProposedResourceService;
import com.rummy.services.RegistrationService;
import com.rummy.services.ReqResourceService;
import com.rummy.services.RequirementService;
import com.rummy.services.ResourceExperienceService;
import com.rummy.services.ResourceService;
import com.rummy.util.MongoAdvancedQuery;
import com.rummy.util.MongoEqualQuery;
import com.rummy.util.MongoOrderByEnum;
import com.rummy.util.MongoSortVO;

/**
 * 
 * @author skkhadar
 *
 */
@Component
@Path("/resource")
public class ResourceResource {

	@Autowired
	private ResourceService resourceService;

	@Autowired
	private ResourceExperienceService resourceExpService;
	@Autowired
	private RegistrationService registrationService;
	@Autowired
	private RequirementService requirementService;
	@Autowired
	private ProposedResourceService proposedResource;
	@Autowired
	private ReqResourceService reqResourceService;

	Logger logger = Logger.getLogger(ResourceResource.class);

	/**
	 * 
	 * @param resourceMapper
	 * @return
	 */
	@Path("/createResource")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public com.rummy.response.Response createResource(@RequestBody ResourceMapper resourceMapper) {
		boolean result;
		try {
			Resource resource = new Resource();
			BeanUtils.copyProperties(resourceMapper, resource);
			resource.setStatus("Active");
			resource.setPostedDate(new Date());
			resource.setRegistrationId(new ObjectId(resourceMapper.getRegistrationId()));
			resource.setResId("RES-" + randomNumber());
			String[] ar = resource.getPrimarySkills().split(",");
			Arrays.sort(ar);
			int i = 0;
			StringBuilder values = new StringBuilder();
			for (String string : ar) {
				values.append(string);
				i++;
				if (i != ar.length) {
					values.append(",");
				}
			}
			resource.setPrimarySkills(values.toString());
			resource.setSoftLock("No");
			resource.setHardLock("No");
			result = resourceService.createResource(resource);
			ResourceExperience res = new ResourceExperience();
			res.setMonthsOfExperience(resource.getMonthsOfExperience());
			res.setYearsOfExperience(resource.getYearsOfExperience());
			result = resourceExpService.create(res);
			if (result == false) {
				logger.debug("Resource not created..");
				return new com.rummy.response.Response("Failed", result, HttpStatus.CONFLICT, "not created");
			}
			logger.debug("Resource Created..");
			return new com.rummy.response.Response("Success", result, HttpStatus.OK, "created");

		} catch (RAException e) {
			logger.error("Records not found" + e.getMessage());
			throw new RAException(e.getMessage());
		}
	}

	/**
	 * 
	 * @param uploadFile
	 * @param id
	 * @return
	 * @throws IOException
	 */
	@Path("/uploadFile/{id}")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public com.rummy.response.Response uploadFile(@FormDataParam("uploadFile") InputStream uploadFile,
			@PathParam("id") ObjectId id) throws IOException {
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("_id", id);
			Resource res;
			try {
				res = resourceService.findOneByCondition(condition);
			} catch (RAException e) {
				logger.error("flie not uploaded.." + e.getMessage());
				return new com.rummy.response.Response(HttpStatus.CONFLICT, "No records found");
			}
			res.setUploadResume(IOUtils.toByteArray(uploadFile));
			Boolean result = resourceService.saveResource(res);
			if (result == false) {
				logger.debug("flie not uploaded for id:" + id);
				return new com.rummy.response.Response("Failed", result, HttpStatus.CONFLICT, "not uploaded");
			}
			logger.debug("flie uploaded successfully for id" + id);
			return new com.rummy.response.Response("Success", result, HttpStatus.OK, "uploaded");
		} catch (RAException e) {
			logger.error("flie not uploaded.." + e.getMessage());
			throw new RAException(e.getMessage());
		}
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws IOException
	 */
	@Path("/downloadFile/{id}")
	@GET
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response downloadFile(@PathParam("id") final ObjectId id) throws IOException {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("_id", id);
		Resource resource;
		try {
			resource = resourceService.findOneByCondition(condition);
		} catch (RAException e) {
			logger.error("flie not downloaded.." + e.getMessage());
			throw new RAException("Data Not Found !!");
		}
		final byte[] doc = resource.getUploadResume();
		String filename = resource.getFirstName();

		StreamingOutput fileStream = new StreamingOutput() {
			@Override
			public void write(java.io.OutputStream output) throws IOException, WebApplicationException {
				try {
					output.write(doc);
					output.flush();
				} catch (Exception e) {
					logger.error("flie not downloaded.." + e.getMessage());
					throw new RAException("File Not Found !!");
				}
			}
		};
		return Response.ok(fileStream).header("content-disposition", "attachment; filename = " + filename + ".docx")
				.build();
	}

	/**
	 * 
	 * @param uploadFile
	 * @param id
	 * @return
	 * @throws IOException
	 */
	@Path("/bulkUpload/{id}")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public com.rummy.response.Response bulkUpload(@FormDataParam("uploadFile") InputStream uploadFile,
			@PathParam("id") ObjectId id) throws IOException {
		boolean result = false;
		try {
			Resource resource = new Resource();
			resource.setRegistrationId(id);
			XSSFWorkbook workbook = new XSSFWorkbook(uploadFile);
			XSSFSheet sheet = workbook.getSheetAt(0);
			int col_value = sheet.getRow(0).getLastCellNum();
			int row_num = sheet.getLastRowNum();
			List<String> DBheader = new ArrayList<String>();
			List<String> Data = new ArrayList<String>();

			for (int z = 1; z <= row_num; z++) {
				DBheader.clear();
				Data.clear();
				for (int j = 0; j < col_value; j++) {
					if (sheet.getRow(0).getCell(j) != null || sheet.getRow(0) != null) {
						String cel_value = sheet.getRow(0).getCell(j).toString();
						DBheader.add(cel_value.trim());
					} else {
						break;

					}
				}
				for (int k = 0; k < col_value; k++) {
					String data = " ";
					if (sheet.getRow(z).getCell(k) != null) {
						data = sheet.getRow(z).getCell(k).toString();
					}
					Data.add(data.trim());
				}
				BasicDBObject doc = new BasicDBObject();
				int l = 0;
				for (String headers : DBheader) {
					if (l > Data.size()) {
						break;
					}
					doc.append(headers, Data.get(l));
					l++;
				}
				resource.setBulkUpload(doc);
				result = resourceService.createResource(resource);
			}

			if (result == false) {
				logger.debug("flie not bulkuploaded for id :" + id);
				return new com.rummy.response.Response("Failed", result, HttpStatus.CONFLICT, "not uploaded");
			}
			logger.debug("flie bulkuploaded successfully for id :" + id);
			return new com.rummy.response.Response("Success", result, HttpStatus.OK, "uploaded");
		} catch (RAException e) {
			logger.error("flie not uploaded.." + e.getMessage());
			throw new RAException(e.getMessage());
		}
	}

	/**
	 * 
	 * @param resourceMapper
	 * @param id
	 * @return
	 */
	@Path("/saveResource/{id}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public com.rummy.response.Response saveResource(@RequestBody ResourceMapper resourceMapper,
			@PathParam("id") ObjectId id) {
		try {
			Resource resource = new Resource();
			BeanUtils.copyProperties(resourceMapper, resource);
			resource.set_id(id);
			resource.setRegistrationId(new ObjectId(resourceMapper.getRegistrationId()));
			String[] ar = resource.getPrimarySkills().split(",");
			Arrays.sort(ar);
			int i = 0;
			StringBuilder values = new StringBuilder();
			for (String string : ar) {
				values.append(string);
				i++;
				if (i != ar.length) {
					values.append(",");
				}
			}
			resource.setPrimarySkills(values.toString());
			Boolean result = resourceService.saveResource(resource);
			if (result == false) {
				logger.debug("flie not updated for id" + id);
				return new com.rummy.response.Response("Failed", result, HttpStatus.CONFLICT, "not updated");
			}
			logger.debug("flie updated for id" + id);
			return new com.rummy.response.Response("Success", result, HttpStatus.OK, "updated");

		} catch (RAException e) {
			logger.error("flie not updated " + e.getMessage());
			return new com.rummy.response.Response(HttpStatus.CONFLICT, "file not updated");
		}
	}

	/**
	 * 
	 * @param context
	 * @param value
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@Path("/findOneByPrimaryId/{value}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public com.rummy.response.Response findOneByPrimaryId(@Context ServletContext context,
			@PathParam("value") ObjectId value) throws JsonGenerationException, JsonMappingException, IOException {
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("_id", value);
			Resource resource;
			ResourceMapper mapper;
			try {
				resource = resourceService.findOneByCondition(condition);
				mapper = convertDomainToMappar(resource);
			} catch (RAException e) {
				logger.error("No records found" + e.getMessage());
				return new com.rummy.response.Response(HttpStatus.CONFLICT, "No records found");
			}
			if (mapper == null) {
				logger.debug("No records found for value" + value);
				return new com.rummy.response.Response("Failed", mapper, HttpStatus.CONFLICT, "No record found");
			}
			logger.debug("records found for value" + value);
			return new com.rummy.response.Response("Success", mapper, HttpStatus.OK, "record found");
		} catch (RAException e) {
			logger.error("No records found" + e.getMessage());
			return new com.rummy.response.Response(HttpStatus.CONFLICT, "records found");
		}
	}

	/**
	 * 
	 * @param context
	 * @param objectId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
//new modified /vendor/resource
	//venkat
	@Path("/findResourcesByRegistrationId/{registrationId}/{pageNo}/{pageSize}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public com.rummy.response.Response findResourcesByRegistrationId(@Context ServletContext context,
			@PathParam("registrationId") String objectId, @PathParam("pageNo") int pageNo,
			@PathParam("pageSize") int pageSize) {
		Map<String, Map<String, Integer>> allLists = null;
		int counts=0;
		int pagess=0;
		try {
			MongoEqualQuery equal = new MongoEqualQuery();
			equal.setEqualto(new ObjectId(objectId));
			Map<String, MongoAdvancedQuery> resourceMappingcondition = new HashMap<String, MongoAdvancedQuery>();
			resourceMappingcondition.put("registrationId", equal);
			MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryKey("registrationId");
			sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);
			List<Resource> resourcelist;
			List<ResourceMapper> mapperList;
			BasicDBObject andQuery = new BasicDBObject();
			List<BasicDBObject> c = new ArrayList<BasicDBObject>();
			c.add(new BasicDBObject("registrationId", new ObjectId(objectId)));
			andQuery.put("$and", c);

			// int count = resourceService.getCount(andQuery);
			//int count = resourceService.getCount(resourceMappingcondition);
			// int pages = resourceService.getPages(sort, pageSize);
			//int pages = count / pageSize + ((count % pageSize) > 0 ? 1 : 0);
			try {
				resourcelist = resourceService.advancedFindByCondition(resourceMappingcondition, sort, pageNo,
						pageSize);
				counts = resourceService.getCount(sort);
				pagess = resourceService.getPages(sort, pageSize);
				 
				

				if (null != resourcelist) {
					allLists = allTLists(resourcelist);
				}
				mapperList = convertDomainToMapperList(resourcelist);
			} catch (RAException e) {
				logger.error("No records found" + e.getMessage());
				return new com.rummy.response.Response("error", pagess, counts, HttpStatus.CONFLICT,
						"No records found");
			}
			if (mapperList == null || mapperList.size() == 0) {

				return new com.rummy.response.Response(mapperList, allLists, pagess, counts, HttpStatus.CONFLICT,
						"No records found");
			}

			return new com.rummy.response.Response(mapperList, allLists, pagess, counts, HttpStatus.OK,
					"records found");

		} catch (RAException e) {
			logger.error("No records found" + e.getMessage());
			return new com.rummy.response.Response(HttpStatus.CONFLICT, "records found");
		}
	}

	/**
	 * 
	 * @param context
	 * @param objectId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@Path("/findSowResourcesByRegId/{registrationId}/{pageNo}/{pageSize}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public com.rummy.response.Response findSowResourcesByRegistrationId(@Context ServletContext context,
			@PathParam("registrationId") String objectId, @PathParam("pageNo") int pageNo,
			@PathParam("pageSize") int pageSize) {
		Map<String, Map<String, Integer>> allLists = null;

		try {
			MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryKey("registrationId");
			sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);
			List<Resource> resourcelist = null;
			List<ResourceMapper> resourceMapperList = null;
			try {
				MongoEqualQuery equal1 = new MongoEqualQuery();
				equal1.setEqualto("No");
				MongoEqualQuery equal2 = new MongoEqualQuery();
				equal2.setEqualto("Active");
				MongoEqualQuery equal3 = new MongoEqualQuery();
				equal2.setEqualto(objectId);
				Map<String, MongoAdvancedQuery> resourceMappingcondition = new HashMap<String, MongoAdvancedQuery>();
				resourceMappingcondition.put("hardLock", equal1);
				resourceMappingcondition.put("status", equal2);
				resourceMappingcondition.put("registrationId", equal3);
				resourcelist = resourceService.advancedFindByCondition(resourceMappingcondition, sort, pageNo,
						pageSize);
			} catch (RAException e) {
				e.getMessage();
			}
			if (null != resourcelist) {
				allLists = allTLists(resourcelist);
			}
			resourceMapperList = convertDomainToMapperList(resourcelist);
			resourceMapperList = addSowStatus(resourceMapperList, objectId);
			resourceMapperList = requestdStatus(resourceMapperList, objectId);
			int count = resourceService.getCount(sort);
			int pages = resourceService.getPages(sort, pageSize);
			if (resourceMapperList == null || resourceMapperList.size() == 0) {

				return new com.rummy.response.Response(resourceMapperList, allLists, pages, count,
						HttpStatus.CONFLICT, "No records found");

			}
			logger.debug("Records found");
			return new com.rummy.response.Response(resourceMapperList, allLists, pages, count, HttpStatus.OK,
					"records found");

			/*
			 * } catch (RAException e) { throw new RAException(e.getMessage());
			 */

		} catch (RAException e) {
			logger.error("No records found " + e.getMessage());
			return new com.rummy.response.Response(HttpStatus.CONFLICT, "records found");

		}

	}

	/**
	 * 
	 * @param context
	 * @param objectId
	 * @param primarySkills
	 * @param jobCategory
	 * @param location
	 * @param experience
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	
	
	
	@Path("/findResourcesBySearch/{registrationId}/{primarySkills}/{jobCategory}/{location}/{experience}/{pageNo}/{pageSize}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public com.rummy.response.Response findResourcesBySearch(@Context ServletContext context,
			@PathParam("registrationId") String objectId, @PathParam("primarySkills") String primarySkills,
			@PathParam("jobCategory") String jobCategory, @PathParam("location") String location,
			@PathParam("experience") String experience, @PathParam("pageNo") int pageNo,
			@PathParam("pageSize") int pageSize) {
		Map<String, Map<String, Integer>> allLists = null;
		try {
			MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryKey("_id");
			sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);
			/*String[] ar = primarySkills.split(",");
			Arrays.sort(ar);
			int i = 0;
			StringBuilder values = new StringBuilder();
			for (String string : ar) {
				values.append(string);
				i++;
				if (i != ar.length) {
					values.append(",");
				}
			}
			String ps = values.toString();*/

			List<Resource> resourcelist = null;
			BasicDBObject andQuery = new BasicDBObject();
			List<BasicDBObject> c = new ArrayList<BasicDBObject>();
			if (!primarySkills.equals("undefined"))
				c.add(new BasicDBObject("primarySkills", primarySkills));
			if (!jobCategory.equals("undefined"))
				c.add(new BasicDBObject("jobCategory", jobCategory));
			if (!location.equals("undefined"))
				c.add(new BasicDBObject("currentLocation", location));
			if (!experience.equals("undefined"))
				c.add(new BasicDBObject("yearsOfExperience", experience));
			c.add(new BasicDBObject("status", "Active"));
			c.add(new BasicDBObject("hardLock", "No"));
			c.add(new BasicDBObject("registrationId", new ObjectId(objectId)));
			andQuery.put("$and", c);
			try {
				resourcelist = resourceService.findAllByCondition(andQuery, sort, pageNo, pageSize);

			} catch (RAException e) {
				e.getMessage();
			}
			int count = resourceService.getCount(andQuery);
			// int pages =resourceService.getPages(sort, pageSize);
			int pages = count / pageSize + ((count % pageSize) > 0 ? 1 : 0);
			List<Resource> list = new ArrayList<Resource>();
			if (null != resourcelist)
				for (Resource resource : resourcelist) {
					if (resource.getRegistrationId().toString().equalsIgnoreCase(objectId)) {
						list.add(resource);
					}
				}
			if (null != list) {

				allLists = allTLists(list);
			}
			List<ResourceMapper> mapperList = null;
			try {
				mapperList = convertDomainToMapperList(list);
			
			} catch (RAException e) {
				e.getMessage();
			}
			if (null == mapperList || mapperList.size() == 0) {

				return new com.rummy.response.Response(mapperList, allLists, pages, count, HttpStatus.OK,
						"No records found");

			}

			return new com.rummy.response.Response(mapperList, allLists, pages, count, HttpStatus.OK,
					"records found");

		} catch (RAException e) {
			logger.error("No records found " + e.getMessage());
			return new com.rummy.response.Response(HttpStatus.CONFLICT, "records not found");
		}
	}
	// new modified /vendor /allrequirements /searchresource
		@Path("/vendorfindResourcesBySearch/{registrationId}/{requirementId}/{jobCategory}/{experience}/{pageNo}/{pageSize}")
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Consumes(MediaType.APPLICATION_JSON)
		public com.rummy.response.Response vendorFindResourcesBySearch(@Context ServletContext context,
				@PathParam("registrationId") String registrationId, @PathParam("requirementId") String requirementId,
				@PathParam("jobCategory") String jobCategory, @PathParam("experience") String experience,
				@PathParam("pageNo") int pageNo, @PathParam("pageSize") int pageSize) {
			// varriable declared
			List<ResourceMapper> resourceMappperList = null;
			com.rummy.response.Response response = null;
			try {
				MongoSortVO sort = new MongoSortVO();
				sort.setPrimaryKey("_id");
				sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);

				List<Resource> resourcelist = null;
				BasicDBObject basicDBObject = new BasicDBObject();
				if (null != registrationId)
					basicDBObject.put("registrationId", new ObjectId(registrationId));
				if (null != experience)
					basicDBObject.put("yearsOfExperience", new BasicDBObject("$regex", experience).append("$options", "i"));
				if (null != jobCategory)
					basicDBObject.put("jobCategory", new BasicDBObject("$regex", jobCategory).append("$options", "i"));
			basicDBObject.put("hardLock", new BasicDBObject("$regex", "No").append("$options", "i"));
				basicDBObject.put("status", new BasicDBObject("$regex", "Active").append("$options", "i"));

				try {
					resourcelist = resourceService.findAllByCondition(basicDBObject, sort, pageNo, pageSize);
					if (null != resourcelist && 0 != resourcelist.size()) {

						resourceMappperList = convertDomainToMapperList(resourcelist);
				//adding business status		
						resourceMappperList = vendorproposedStatus(resourceMappperList, registrationId, requirementId); 
						if (null != resourceMappperList && 0 != resourceMappperList.size()) {
							response = new com.rummy.response.Response(HttpStatus.OK, "records are found",
									resourceMappperList);
							logger.error("records found ");
						} 
						}else {
							response = new com.rummy.response.Response(HttpStatus.OK, "sorry,norecors are found",
									resourceMappperList);
							logger.error("No records found ");

					}
				} catch (RAException e) {
					e.getMessage();
					response = new com.rummy.response.Response(HttpStatus.CONFLICT, "error occured", resourceMappperList);
					logger.error("No records found " + e.getMessage());

				}
			} catch (RAException e) {
				e.getMessage();
				response = new com.rummy.response.Response(HttpStatus.CONFLICT, "error occured", resourceMappperList);
				logger.error("No records found " + e.getMessage());
			}

			return response;
		}
	/**
	 * 
	 * @param context
	 * @param primarySkills
	 * @param jobCategory
	 * @param location
	 * @param experience
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@Path("/findResourcesBySearch/{primarySkills}/{jobCategory}/{location}/{experience}/{pageNo}/{pageSize}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public com.rummy.response.Response findResourcesBySearchs(@Context ServletContext context,
			@PathParam("primarySkills") String primarySkills, @PathParam("jobCategory") String jobCategory,
			@PathParam("location") String location, @PathParam("experience") String experience,
			@PathParam("pageNo") int pageNo, @PathParam("pageSize") int pageSize) {
		Map<String, Map<String, Integer>> allLists = null;
		try {
			MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryKey("_id");
			sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);

			String[] ar = primarySkills.split(",");
			Arrays.sort(ar);
			int i = 0;
			StringBuilder values = new StringBuilder();
			for (String string : ar) {
				values.append(string);
				i++;
				if (i != ar.length) {
					values.append(",");
				}
			}
			String ps = values.toString();
			int count = resourceService.getCount(sort);
			int pages = resourceService.getPages(sort, pageSize);
			List<Resource> resourcelist = null;

			BasicDBObject andQuery = new BasicDBObject();
			List<BasicDBObject> c = new ArrayList<BasicDBObject>();
			if (!ps.equals("undefined"))
				c.add(new BasicDBObject("primarySkills", ps));
			if (!jobCategory.equals("undefined"))
				c.add(new BasicDBObject("jobCategory", jobCategory));
			if (!location.equals("undefined"))
				c.add(new BasicDBObject("currentLocation", location));
			if (!experience.equals("undefined"))
				c.add(new BasicDBObject("yearsOfExperience", experience));
			c.add(new BasicDBObject("status", "Active"));
			c.add(new BasicDBObject("hardLock", "No"));
			andQuery.put("$and", c);
			try {
				resourcelist = resourceService.findAllByCondition(andQuery, sort, pageNo, pageSize);
			} catch (RAException e) {
				e.getMessage();
			}

			if (null != resourcelist) {

				allLists = allTLists(resourcelist);
			}
			List<ResourceMapper> mapperList = null;
			try {
				mapperList = convertDomainToMapperList(resourcelist);
			} catch (RAException e) {
				e.getMessage();
			}
			if (null == mapperList || mapperList.size() == 0) {

				return new com.rummy.response.Response(mapperList, allLists, pages, count, HttpStatus.NO_CONTENT,
						"No records found");

			}

			return new com.rummy.response.Response(mapperList, allLists, pages, count, HttpStatus.OK,
					"records found");

		} catch (RAException e) {
			logger.error("No Records found" + e.getMessage());
			return new com.rummy.response.Response(HttpStatus.CONFLICT, "records not found");
		}
	}

	/**
	 * 
	 * @param context
	 * @param objectId
	 * @param primarySkills
	 * @param jobCategory
	 * @param location
	 * @param experience
	 * @param vendors
	 * @param budget
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	
	//new modified /vendor/ resources/ side filter
	
	@Path("/findResourcesBySearchs/{registrationId}/{primarySkills}/{jobCategory}/{location}/{experience}/{customers}/{budget}/{pageNo}/{pageSize}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public com.rummy.response.Response findResourcesBySearchs(@Context ServletContext context,
			@PathParam("registrationId") String objectId, @PathParam("primarySkills") String primarySkills,
			@PathParam("jobCategory") String jobCategory, @PathParam("location") String location,
			@PathParam("experience") String experience, @PathParam("customers") String customers,
			@PathParam("budget") String budget, @PathParam("pageNo") int pageNo, @PathParam("pageSize") int pageSize) {
		try {
			MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryKey("_id");
			sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);

			/*String[] ar = primarySkills.split(",");
			Arrays.sort(ar);
			int i = 0;
			StringBuilder values = new StringBuilder();
			for (String string : ar) {
				values.append(string);
				i++;
				if (i != ar.length) {
					values.append(",");
				}
			}
			String ps = values.toString();*/

			int pages = resourceService.getPages(sort, pageSize);
			//int count = resourceService.getCount(sort);
			List<Resource> resourcelist = null;
			try {	
				
				resourcelist = resourceService.advancedFindByConditions(primarySkills, jobCategory, location, experience,sort, pageNo, pageSize);
			} catch (RAException e) {
				e.getMessage();
			}
			List<ResourceMapper> mapperList = null;
			mapperList = convertDomainToMapperList(resourcelist);
			//List<ResourceMapper> mapperList1 = null;
			int count = mapperList.size();
			if (mapperList == null || mapperList.size() == 0) {
				//mapperList1 = optionList(objectId, pageNo, pageSize);
				logger.debug("Records found");
				return new com.rummy.response.Response(mapperList, pages, count, HttpStatus.NO_CONTENT, "records not found");
			}
			logger.debug("No Records found");
			return new com.rummy.response.Response(mapperList, pages, count, HttpStatus.OK, "records found");
		} catch (RAException e) {
			logger.error("No Records found" + e.getMessage());
			return new com.rummy.response.Response(HttpStatus.CONFLICT, "records not found");
		}
	}

	/**
	 * 
	 * @param context
	 * @param value
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@Path("/findResourcesByBudget/{budget}/{pageNo}/{pageSize}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public com.rummy.response.Response findReqResourcesByBudget(@Context ServletContext context,
			@PathParam("budget") String value, @PathParam("pageNo") int pageNo, @PathParam("pageSize") int pageSize) {
		try {
			MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryKey("_id");
			sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);
			String name = "budget";
			List<Resource> resourcelist;
			List<ResourceMapper> mapperList;
			try {
				resourcelist = resourceService.advancedFindByCondition(name, value, sort, pageNo, pageSize);
				mapperList = convertDomainToMapperList(resourcelist);
			} catch (RAException e) {
				return new com.rummy.response.Response(HttpStatus.CONFLICT, "No records found");
			}
			int count = resourceService.getCount(sort);
			int pages = resourceService.getPages(sort, pageSize);
			if (mapperList == null || mapperList.size() == 0) {
				logger.debug("No Records found");
				return new com.rummy.response.Response(mapperList, pages, count, HttpStatus.CONFLICT,
						"No records found");
			}
			logger.debug("Records found");
			return new com.rummy.response.Response(mapperList, pages, count, HttpStatus.OK, "records found");
		} catch (RAException e) {
			logger.error("No Records found" + e.getMessage());
			return new com.rummy.response.Response(HttpStatus.CONFLICT, "records not found");
		}
	}

	/**
	 * 
	 * @param context
	 * @param nameOftheProperty
	 * @param valueOftheProperty
	 * @return
	 */
	@Path("/findOneByCondition/{nameOftheProperty}/{valueOftheProperty}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public com.rummy.response.Response findOneByCondition(@Context ServletContext context,
			@PathParam("nameOftheProperty") String nameOftheProperty,
			@PathParam("valueOftheProperty") String valueOftheProperty) {
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put(nameOftheProperty, valueOftheProperty);
			Resource resource;
			ResourceMapper mapper;
			try {
				resource = resourceService.findOneByCondition(condition);
				mapper = convertDomainToMappar(resource);
			} catch (RAException e) {
				logger.error("No Records found" + e.getMessage());
				return new com.rummy.response.Response(HttpStatus.CONFLICT, "No records found");
			}
			if (mapper == null) {
				logger.debug("No Records found");
				return new com.rummy.response.Response("Failed", mapper, HttpStatus.CONFLICT, "No record found");
			}
			logger.debug("Records found");
			return new com.rummy.response.Response("Success", mapper, HttpStatus.OK, "record found");
		} catch (RAException e) {
			logger.error("No Records found" + e.getMessage());
			return new com.rummy.response.Response(HttpStatus.CONFLICT, "records not found");
		}
	}

	/**
	 * 
	 * @param context
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@Path("/listResources/{pageNo}/{pageSize}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public com.rummy.response.Response listResources(@Context ServletContext context, @PathParam("pageNo") int pageNo,
			@PathParam("pageSize") int pageSize) {
		try {
			MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryKey("_id");
			sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);
			List<Resource> list;
			List<ResourceMapper> mapperList;
			try {
				list = resourceService.advancedFindByCondition(sort, pageNo, pageSize);
				mapperList = convertDomainToMapperList(list);
			} catch (RAException e) {
				logger.error("No Records found" + e.getMessage());
				return new com.rummy.response.Response(HttpStatus.CONFLICT, "No records found");
			}
			Map<String, Map<String, Integer>> allList = allTLists(list);
			int count = resourceService.getCount(sort);
			int pages = resourceService.getPages(sort, pageSize);
			if (mapperList == null || mapperList.size() == 0) {
				logger.debug("No Records found");
				return new com.rummy.response.Response(mapperList, allList, pages, count, HttpStatus.CONFLICT,
						"No records found");
			}
			logger.debug("Records found");
			return new com.rummy.response.Response(mapperList, allList, pages, count, HttpStatus.OK, "records found");
		} catch (RAException e) {
			logger.error("No Records found" + e.getMessage());
			return new com.rummy.response.Response(HttpStatus.CONFLICT, "records not found");
		}
	}

	/**
	 * 
	 * @param context
	 * @param nameOftheProperty
	 * @param valueOftheProperty
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@Path("/findOneAllByCondition/{nameOftheProperty}/{valueOftheProperty}/{pageNo}/{pageSize}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public com.rummy.response.Response findOneAllByCondition(@Context ServletContext context,
			@PathParam("nameOftheProperty") String nameOftheProperty,
			@PathParam("valueOftheProperty") String valueOftheProperty, @PathParam("pageNo") int pageNo,
			@PathParam("pageSize") int pageSize) {
		try {
			MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryKey("_id");
			sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);
			List<Resource> resourcelist;
			List<ResourceMapper> mapperList;
			try {
				resourcelist = resourceService.advancedFindByCondition(nameOftheProperty, valueOftheProperty, sort,
						pageNo, pageSize);
				mapperList = convertDomainToMapperList(resourcelist);
			} catch (RAException e) {
				logger.error("No Records found" + e.getMessage());
				return new com.rummy.response.Response(HttpStatus.CONFLICT, "No records found");
			}
			int count = resourceService.getCount(sort);
			int pages = resourceService.getPages(sort, pageSize);
			if (mapperList == null || mapperList.size() == 0) {
				logger.debug("No Records found");
				return new com.rummy.response.Response(mapperList, pages, count, HttpStatus.CONFLICT,
						"No records found");
			}
			logger.debug("Records found");
			return new com.rummy.response.Response(mapperList, pages, count, HttpStatus.OK, "records found");
		} catch (RAException e) {
			logger.error("No Records found" + e.getMessage());
			return new com.rummy.response.Response(HttpStatus.CONFLICT, "records not found");
		}
	}

	/**
	 * 
	 * @param context
	 * @param objectId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@Path("/findResourcesByRequirementId/{requirementId}/{pageNo}/{pageSize}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public com.rummy.response.Response findResourcesByRequirementId(@Context ServletContext context,
			@PathParam("requirementId") String objectId, @PathParam("pageNo") int pageNo,
			@PathParam("pageSize") int pageSize) {
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("_id", new ObjectId(objectId));

			MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryKey("_id");
			sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);
			List<Resource> resourcelist;
			List<ResourceMapper> mapperList;
			try {
				resourcelist = resourceService.getByMapObjects(sort, pageNo, pageSize, condition);
				mapperList = convertDomainToMapperList(resourcelist);

			} catch (RAException e) {
				logger.error("No Records found" + e.getMessage());
				return new com.rummy.response.Response(HttpStatus.CONFLICT, "No records found");
			}
			int count = resourceService.getCount(sort);
			int pages = resourceService.getPages(sort, pageSize);
			if (mapperList == null || mapperList.size() == 0) {
				logger.debug("No Records found");
				return new com.rummy.response.Response(mapperList, pages, count, HttpStatus.CONFLICT,
						"No records found");
			}
			logger.debug("Records found");
			return new com.rummy.response.Response(mapperList, pages, count, HttpStatus.OK, "records found");
		} catch (RAException e) {
			logger.error("No Records found" + e.getMessage());
			return new com.rummy.response.Response(HttpStatus.CONFLICT, "records not found");
		}
	}

	/**
	 * 
	 * @param context
	 * @param reqId
	 * @param regId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@Path("/findResourcesByReqIdAndVendorId/{requirementId}/{vendorId}/{pageNo}/{pageSize}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public com.rummy.response.Response findResourcesByReqIdAndVendorId(@Context ServletContext context,
			@PathParam("requirementId") String reqId, @PathParam("vendorId") String regId,
			@PathParam("pageNo") int pageNo, @PathParam("pageSize") int pageSize) {
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("requirementId", new ObjectId(reqId));
			condition.put("vendorId", new ObjectId(regId));

			MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryKey("_id");
			sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);
			List<Resource> resourcelist;
			List<ResourceMapper> mapperList;
			try {
				resourcelist = resourceService.getByMapObjects(sort, pageNo, pageSize, condition);
				mapperList = convertDomainToMapperList(resourcelist);
			} catch (RAException e) {
				logger.error("No Records found" + e.getMessage());
				return new com.rummy.response.Response(HttpStatus.CONFLICT, "No records found");
			}
			int count = resourceService.getCount(sort);
			int pages = resourceService.getPages(sort, pageSize);
			if (mapperList == null || mapperList.size() == 0) {
				logger.debug("No Records found");
				return new com.rummy.response.Response(mapperList, pages, count, HttpStatus.CONFLICT,
						"No records found");
			}
			logger.debug("Records found");
			return new com.rummy.response.Response(mapperList, pages, count, HttpStatus.OK, "records found");
		} catch (RAException e) {
			logger.error("No Records found" + e.getMessage());
			return new com.rummy.response.Response(HttpStatus.CONFLICT, "records not found");
		}
	}

	/**
	 * 
	 * @param context
	 * @param conditions
	 * @param companyId
	 * @param vendorId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@Path("/findResourcesByTwoIds/{conditions}/{companyId}/{vendorId}/{pageNo}/{pageSize}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public com.rummy.response.Response findResourcesByComIdAndVendorId(@Context ServletContext context,
			@PathParam("conditions") String conditions, @PathParam("companyId") String companyId,
			@PathParam("vendorId") String vendorId, @PathParam("pageNo") int pageNo,
			@PathParam("pageSize") int pageSize) {
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			if (conditions.equalsIgnoreCase("CompanyName")) {
				condition.put("companyId", new ObjectId(companyId));
			} else {
				condition.put("requirementId", new ObjectId(companyId));
			}
			condition.put("vendorId", new ObjectId(vendorId));

			MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryKey("_id");
			sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);
			List<Resource> resourcelist;
			List<ResourceMapper> mapperList;
			try {
				resourcelist = resourceService.getByMapObjects(sort, pageNo, pageSize, condition);
				mapperList = convertDomainToMapperList(resourcelist);
			} catch (RAException e) {
				logger.error("No Records found" + e.getMessage());
				return new com.rummy.response.Response(HttpStatus.CONFLICT, "No records found");
			}
			int count = resourceService.getCount(sort);
			int pages = resourceService.getPages(sort, pageSize);
			if (mapperList == null || mapperList.size() == 0) {
				logger.debug("No Records found");
				return new com.rummy.response.Response(mapperList, pages, count, HttpStatus.CONFLICT,
						"No records found");
			}
			logger.debug("Records found");
			return new com.rummy.response.Response(mapperList, pages, count, HttpStatus.OK, "records found");
		} catch (RAException e) {
			logger.error("No Records found" + e.getMessage());
			return new com.rummy.response.Response(HttpStatus.CONFLICT, "records not found");
		}
	}

	/**
	 * 
	 * @param context
	 * @param reqId
	 * @param regId
	 * @param vendorId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@Path("/findResourcesByReqIdAndComIdAndVendorId/{requirementId}/{companyId}/{vendorId}/{pageNo}/{pageSize}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public com.rummy.response.Response findResourcesByReqIdAndComIdAndVendorId(@Context ServletContext context,
			@PathParam("requirementId") String reqId, @PathParam("companyId") String regId,
			@PathParam("vendorId") String vendorId, @PathParam("pageNo") int pageNo,
			@PathParam("pageSize") int pageSize) {
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("requirementId", new ObjectId(reqId));
			condition.put("companyId", new ObjectId(regId));
			condition.put("vendorId", new ObjectId(vendorId));

			MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryKey("_id");
			sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);

			List<Resource> resourcelist;
			List<ResourceMapper> mapperList;
			try {
				resourcelist = resourceService.getByMapObjects(sort, pageNo, pageSize, condition);
				mapperList = convertDomainToMapperList(resourcelist);
			} catch (RAException e) {
				logger.error("No Records found" + e.getMessage());
				return new com.rummy.response.Response(HttpStatus.CONFLICT, "No records found");
			}
			int count = resourceService.getCount(sort);
			int pages = resourceService.getPages(sort, pageSize);
			if (mapperList == null || mapperList.size() == 0) {
				logger.debug("No Records found");
				return new com.rummy.response.Response(mapperList, pages, count, HttpStatus.CONFLICT,
						"No records found");
			}
			logger.debug("Records found");
			return new com.rummy.response.Response(mapperList, pages, count, HttpStatus.OK, "records found");
		} catch (RAException e) {
			logger.error("No Records found" + e.getMessage());
			return new com.rummy.response.Response(HttpStatus.CONFLICT, "records not found");
		}
	}

	/**
	 * 
	 * @param resourceMapper
	 * @param id
	 * @return
	 */
	@PUT
	@Path("/inactiveOrActive/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public com.rummy.response.Response inactiveOrActive(@RequestBody ResourceMapper resourceMapper,
			@PathParam("id") ObjectId id) {
		try {
			Resource resource = new Resource();
			BeanUtils.copyProperties(resourceMapper, resource);
			resource.set_id(id);
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("_id", id);
			Map<String, Object> target = new HashMap<String, Object>();
			target.put("status", resource.getStatus());
			Boolean result = resourceService.updateMapByCondition(condition, target);
			if (result == false) {
				logger.debug("Resource inactive (failed) for id" + id);
				return new com.rummy.response.Response("Failed", HttpStatus.CONFLICT);
			}
			logger.debug("Resource active (success) for id" + id);
			return new com.rummy.response.Response("Success", HttpStatus.OK);

		} catch (RAException e) {
			logger.error("Resource inactive(Failed)" + e.getMessage());
			return new com.rummy.response.Response(HttpStatus.CONFLICT, "Resource inactive(Failed)");
		}
	}

	/**
	 * 
	 * @param resourceMapper
	 * @param id
	 * @return
	 */
	@PUT
	@Path("/softLock/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public com.rummy.response.Response softLock(@RequestBody ResourceMapper resourceMapper,
			@PathParam("id") ObjectId id) {
		try {
			Resource resource = new Resource();
			BeanUtils.copyProperties(resourceMapper, resource);
			resource.set_id(id);
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("_id", id);
			Map<String, Object> target = new HashMap<String, Object>();
			target.put("softLock", resource.getSoftLock());
			Boolean result = resourceService.updateMapByCondition(condition, target);
			if (result == false) {
				logger.debug("Resource softlock failed for id" + id);
				return new com.rummy.response.Response("Failed", HttpStatus.CONFLICT);
			}
			logger.debug("Resource softlock success for id" + id);
			return new com.rummy.response.Response("Success", HttpStatus.OK);

		} catch (RAException e) {
			logger.error("Resource softlock failed" + e.getMessage());
			return new com.rummy.response.Response(HttpStatus.CONFLICT, "Resource softlock fail");
		}
	}

	/**
	 * 
	 * @param resourceMapper
	 * @param id
	 * @return
	 */
	@PUT
	@Path("/hardLock/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public com.rummy.response.Response hardLock(@RequestBody ResourceMapper resourceMapper,
			@PathParam("id") ObjectId id) {
		try {
			Resource resource = new Resource();
			BeanUtils.copyProperties(resourceMapper, resource);
			resource.set_id(id);
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("_id", id);
			Map<String, Object> target = new HashMap<String, Object>();
			target.put("hardLock", resource.getHardLock());
			Boolean result = resourceService.updateMapByCondition(condition, target);
			if (result == false) {
				logger.debug("Resource hardlock failed for id" + id);
				return new com.rummy.response.Response("Failed", HttpStatus.CONFLICT);
			}
			logger.debug("Resource hardlock success for id" + id);
			return new com.rummy.response.Response("Success", HttpStatus.OK);

		} catch (RAException e) {
			logger.error("Resource hardlock success for id" + e.getMessage());
			return new com.rummy.response.Response(HttpStatus.CONFLICT, "Resource hardlock fail");
		}
	}

	/**
	 * 
	 * @param context
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@Path("/findAllByCondition/{pageNo}/{pageSize}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public com.rummy.response.Response findAllCondition(@Context ServletContext context,
			@PathParam("pageNo") int pageNo, @PathParam("pageSize") int pageSize) {
		try {
			MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryKey("_id");
			sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);
			List<Resource> resourcelist;
			List<ResourceMapper> mapperList;
			try {
				resourcelist = resourceService.advancedFindByCondition(sort, pageNo, pageSize);
				mapperList = convertDomainToMapperList(resourcelist);
			} catch (RAException e) {
				logger.debug("No records found" + e.getMessage());
				return new com.rummy.response.Response(HttpStatus.CONFLICT, "No records found");
			}
			int count = resourceService.getCount(sort);
			int pages = resourceService.getPages(sort, pageSize);
			if (mapperList == null || mapperList.size() == 0) {
				logger.debug("No records found");
				return new com.rummy.response.Response(mapperList, pages, count, HttpStatus.CONFLICT,
						"No records found");
			}
			logger.debug("records found");
			return new com.rummy.response.Response(mapperList, pages, count, HttpStatus.OK, "records found");
		} catch (RAException e) {
			logger.error("No Records found" + e.getMessage());
			return new com.rummy.response.Response(HttpStatus.CONFLICT, "No records found");
		}

	}

	/**
	 * 
	 * @param context
	 * @param skills
	 * @param jobCategory
	 * @param location
	 * @param experience
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	
//un known1	
	/*@Path("/findResourcesByFilter/{customerRegid}/{primarySkills}/{jobCategory}/{location}/{experience}/{pageNo}/{pageSize}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public com.ojas.ra.response.Response findResourcesByFilter(@Context ServletContext context,
			@PathParam("customerRegid") String customerRegid, @PathParam("primarySkills") String primarySkills,
			@PathParam("jobCategory") String jobCategory, @PathParam("location") String location,
			@PathParam("experience") String experience, @PathParam("pageNo") int pageNo,
			@PathParam("pageSize") int pageSize) {
		try {
			MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryKey("_id");
			sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);
			String[] ar = primarySkills.split(",");
			Arrays.sort(ar);
			int i = 0;
			StringBuilder values = new StringBuilder();
			for (String string : ar) {
				values.append(string);
				i++;
				if (i != ar.length) {
					values.append(",");
				}
			}
			String skills = values.toString();
			List<Resource> resourcelist = null;
			List<ResourceMapper> resourcemapperList = null;
			try {
				resourcelist = resourceService.advancedFindByConditions(skills, jobCategory, location, experience, sort,
						pageNo, pageSize);
			} catch (RAException e) {
				e.getMessage();
			}
			if (null != resourcelist) {
				resourcemapperList = convertDomainToMapperList(resourcelist);
				resourcemapperList = addSowStatus(resourcemapperList, customerRegid);
			}

			int count = resourceService.getCount(sort);
			int pages = resourceService.getPages(sort, pageSize);
			List<ResourceMapper> mapperList1 = null;
			if (resourcemapperList == null || resourcemapperList.size() == 0) {
				logger.debug("records found");
				mapperList1 = optionList(customerRegid, pageNo, pageSize);
				return new com.ojas.ra.response.Response(mapperList1, pages, count, HttpStatus.OK, "records found");
			}
			logger.debug("No records found");
			return new com.ojas.ra.response.Response(resourcemapperList, pages, count, HttpStatus.OK, "records found");
		} catch (RAException e) {
			logger.error("No Records found" + e.getMessage());
			return new com.ojas.ra.response.Response(HttpStatus.CONFLICT, "No records found");
		}
	}*/

	/**
	 * 
	 * @param context
	 * @param location
	 * @param jobCategory
	 * @param primarySkills
	 * @param experience
	 * @param vendors
	 * @return
	 */
	
	//new modified /customer/ all resource /sidefilter
	@Path("/findResourcesByFilter/{primarySkills}/{jobCategory}/{location}/{experience}/{vendors}/{budget}/{pageNo}/{pageSize}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public com.rummy.response.Response findResourcesByFilters(@Context ServletContext context,
			@PathParam("primarySkills") String primarySkills, @PathParam("jobCategory") String jobCategory,
			@PathParam("location") String location, @PathParam("experience") String experience,
			@PathParam("vendors") String vendors, @PathParam("budget") String budget, @PathParam("pageNo") int pageNo,
			@PathParam("pageSize") int pageSize) {
		try {
			MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryKey("_id");
			sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);
			/*String[] ar = primarySkills.split(",");
			Arrays.sort(ar);
			int i = 0;
			StringBuilder values = new StringBuilder();
			for (String string : ar) {
				values.append(string);
				i++;
				if (i != ar.length) {
					values.append(",");
				}
			}
			String skills = values.toString();*/
			//Map<String, Object> condition = new HashMap<String, Object>();
			//condition.put("companyName", vendors);
			List<Resource> list = null;
			List<Resource> resourcelist = null;
		//	Registration registration = null;
			List<ResourceMapper> mapperList = null;
			/*try {
				registration = registrationService.findOneByCondition(condition);
			} catch (RAException e) {
				e.getMessage();
			}
			if (null != registration) {
				MongoEqualQuery equal = new MongoEqualQuery();
				try {
					equal.setEqualto(registration.get_id());
				} catch (RAException e) {
					e.getMessage();
				}
				Map<String, MongoAdvancedQuery> resourceCondition = new HashMap<String, MongoAdvancedQuery>();
				resourceCondition.put("registrationId", equal);
				try {
					list = resourceService.advancedFindByCondition(resourceCondition, sort, pageNo, pageSize);
				} catch (RAException e) {
					e.getMessage();
				}
			}*/
			Map<String, Map<String, Integer>> allLists = null;
			try {
				resourcelist = resourceService.advancedFindByConditions(null,primarySkills, jobCategory, location, experience,
						vendors, budget, sort, pageNo, pageSize);
			//	if (null != list)
			//		resourcelist.addAll(list);
				
				if (null != resourcelist) {

					allLists = allTLists(resourcelist);
					mapperList = convertDomainToMapperList(resourcelist);
				}
				
			} catch (RAException e) {
				e.getMessage();
			}
			int count = resourceService.getCount(sort);
			int pages = resourceService.getPages(sort, pageSize);
			List<ResourceMapper> mapperList1 = null;
			if (mapperList == null || mapperList.size() == 0) {
				MongoSortVO sort1 = new MongoSortVO();
				sort1.setPrimaryKey("registrationId");
				sort1.setPrimaryOrderBy(MongoOrderByEnum.DESC);
				List<Resource> resourcelist1;
				try {
					MongoEqualQuery equal1 = new MongoEqualQuery();
					equal1.setEqualto("NO");
					MongoEqualQuery equal2 = new MongoEqualQuery();
					equal2.setEqualto("Active");
					Map<String, MongoAdvancedQuery> resourceMappingcondition = new HashMap<String, MongoAdvancedQuery>();
					resourceMappingcondition.put("hardLock", equal1);
					resourceMappingcondition.put("status", equal2);
					resourcelist1 = resourceService.advancedFindByCondition(resourceMappingcondition, sort, pageNo,
							pageSize);
					if (null != resourcelist1) {

						allLists = allTLists(resourcelist1);
						mapperList1 = convertDomainToMapperList(resourcelist1);
					}
					
					return new com.rummy.response.Response(mapperList1, allLists, pages, count, HttpStatus.OK, "records found");
				} catch (RAException e) {
					logger.error("No records found" + e.getMessage());
					e.getMessage();
				}
			}
			logger.debug("records found");
			return new com.rummy.response.Response(mapperList, allLists, pages, count, HttpStatus.OK, "records found");
		} catch (RAException e) {
			logger.error("No Records found" + e.getMessage());
			return new com.rummy.response.Response(HttpStatus.CONFLICT, "No records found");
		}
	}

	/**/

	/**
	 * 
	 * @param context
	 * @return
	 */
	@Path("/allVendors")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public com.rummy.response.Response getAllVendors(@Context ServletContext context) {
		Set<String> vendorSet = new LinkedHashSet<String>();
		try {
			MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryKey("_id");
			sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);
			List<Resource> resourceList;
			try {
				resourceList = resourceService.getAllObjects(sort);
			} catch (RAException e) {
				logger.error("No records found" + e.getMessage());
				return new com.rummy.response.Response(HttpStatus.CONFLICT, "No records found");
			}
			for (Resource resource : resourceList) {
				Map<String, Object> condition = new HashMap<String, Object>();
				condition.put("_id", resource.getRegistrationId());
				Registration reg;
				try {
					reg = registrationService.findOneByCondition(condition);
				} catch (RAException e) {
					logger.error("No records found" + e.getMessage());
					return new com.rummy.response.Response(HttpStatus.CONFLICT, "No records found");
				}
				vendorSet.add(reg.getCompanyName());
				if (resourceList == null || resourceList.size() == 0) {
					logger.debug("No records found (Failed)");
					return new com.rummy.response.Response(vendorSet, HttpStatus.CONFLICT, "failed");
				}
			}
		} catch (RAException e) {
			logger.error("No Records found" + e.getMessage());
			return new com.rummy.response.Response(HttpStatus.CONFLICT, "No records found");
		}
		logger.debug("records found");
		return new com.rummy.response.Response(vendorSet, HttpStatus.OK, "success");
	}

	/**
	 * 
	 * @param context
	 * @return resourcesByCityAndTechnology
	 */

	@Path("/resourcesByCityAndTechnology")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)

	public com.rummy.response.Response resourcesByCityAndTechnology(@Context final ServletContext context) {
		com.rummy.response.Response response = null;
		final List<ObjectMapper2> obmlist = new ArrayList<ObjectMapper2>();
		final MongoSortVO sort = new MongoSortVO();
		sort.setPrimaryKey("_id");
		sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);
		final List<Resource> resourceList = resourceService.getAllObjects(sort);
		final Map<String, Object> map = new ConcurrentHashMap<String, Object>();
		if (!resourceList.isEmpty()) {
			final List<Map<String, Object>> reqMap = resourceByTechnologyAndCity(resourceList);
			final Set<String> skillSet = skillSet(resourceList);
			for (final String skill : skillSet) {
				obmlist.add(new ObjectMapper2(skill, skill));
			}
			map.put("first", reqMap);
			map.put("second", obmlist);
			response = new com.rummy.response.Response(map, HttpStatus.OK, "success");
		} else {
			response = new com.rummy.response.Response("no resources found", HttpStatus.CONFLICT, "failed");
		}
		return response;
	}

	/*
	 * @param context
	 * 
	 * @return resourcesByCityAndTechnology
	 */

	@Path("/resourcesByCityAndTechnologyByvendor/{vendorRegId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)

	public com.rummy.response.Response resourcesByCityAndTechnologyByvendor(@Context final ServletContext context,
			@PathParam("vendorId") final ObjectId vendorId) {
		com.rummy.response.Response response = null;
		final List<ObjectMapper2> obmlist = new ArrayList<ObjectMapper2>();
		final MongoSortVO sort = new MongoSortVO();
		sort.setPrimaryKey("_id");
		sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);
		final Map<String, Object> condition = new ConcurrentHashMap<String, Object>();
		condition.put("registrationId", vendorId);
		final List<Resource> resourceList = resourceService.findAllByCondition(condition, sort);
		final Map<String, Object> map = new ConcurrentHashMap<String, Object>();
		if (!resourceList.isEmpty()) {
			final List<Map<String, Object>> reqMap = resourceByTechnologyAndCity(resourceList);
			final Set<String> skillSet = skillSet(resourceList);
			for (final String skill : skillSet) {
				obmlist.add(new ObjectMapper2(skill, skill));
			}
			map.put("first", reqMap);
			map.put("second", obmlist);
			response = new com.rummy.response.Response(map, HttpStatus.OK, "success");
		} else {
			response = new com.rummy.response.Response("no resources found", HttpStatus.CONFLICT, "failed");
		}
		return response;
	}

	/**
	 * 
	 * @param context
	 * @return Get The Top vendor from the Resource Table
	 */
	@Path("/topVendors")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public com.rummy.response.Response topVendors(@Context ServletContext context) {
		try {
			MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryKey("_id");
			sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);
			LinkedHashSet<String> reverseSortedMap = new LinkedHashSet<String>();
			Map<String, Integer> findTopvendors = resourceService.findTopVendors(sort);
			logger.error("top vendors find..");
			Set<Entry<String, Integer>> set = findTopvendors.entrySet();
			List<Entry<String, Integer>> Topvendors = new ArrayList<Entry<String, Integer>>(set);
			Collections.sort(Topvendors, new Comparator<Map.Entry<String, Integer>>() {
				public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
					return o2.getValue().compareTo(o1.getValue());
				}
			});
			for (Map.Entry<String, Integer> entry : Topvendors) {
				reverseSortedMap.add(entry.getKey());
			}
			logger.error("records added..");
			if (reverseSortedMap == null || reverseSortedMap.isEmpty()) {
				return new com.rummy.response.Response("failed", HttpStatus.CONFLICT, reverseSortedMap);
			}
			return new com.rummy.response.Response("success", HttpStatus.OK, reverseSortedMap);
		} catch (RAException e) {
			logger.error("No Records found" + e.getMessage());
			return new com.rummy.response.Response(HttpStatus.CONFLICT, "No records found");
		}
	}

	/**
	 * 
	 * @param context
	 * @param vendorId
	 * @return
	 */
	@Path("/totalResourceByTechnology/{vendorId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public com.rummy.response.Response totalResourceByTechnology(final @Context ServletContext context,
			final @PathParam("vendorId") ObjectId vendorId) {
		final Map<String, Integer> allResources = new ConcurrentHashMap<String, Integer>();
		com.rummy.response.Response response = null;
		String skill = null;
		try {
			final MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryKey("_id");
			sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);
			final Map<String, Object> condition = new ConcurrentHashMap<String, Object>();
			condition.put("registrationId", vendorId);

			final List<Resource> resources = resourceService.findAllByCondition(condition, sort);
			logger.debug("all resources found..");
			if (null != resources) {
				for (final Resource res : resources) {
					skill = res.getPrimarySkills();
					final String[] skills = skill.split(",");
					for (final String str : skills) {
						if (allResources.containsKey(str)) {
							allResources.put(str, allResources.get(str) + 1);
						} else {
							allResources.put(str, 1);
						}
					}
				}
			}
			final List<ObjectMapper> list = converter(allResources);
			response = new com.rummy.response.Response(HttpStatus.OK, list, "succes");
		} catch (RAException e) {
			response = new com.rummy.response.Response(HttpStatus.CONFLICT, "No records found");
			throw new RAException(e.getMessage());
		}
		return response;
	}

	/**
	 * 
	 * @return
	 */
	@Path("/resourceCountByJobCategory")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public com.rummy.response.Response getResourceCountByJobCategory(@Context ServletContext context) {
		com.rummy.response.Response response = null;
		try {
			final MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryKey("_id");
			response = new com.rummy.response.Response("Records not found", HttpStatus.CONFLICT);
			List<Resource> resources = resourceService.getAllObjects(sort);
			Map<String, Integer> resourceCount = new HashMap<String, Integer>();
			for (final Resource resource : resources) {
				if (resourceCount.containsKey(resource.getJobCategory())) {
					resourceCount.put(resource.getJobCategory(), 1 + resourceCount.get(resource.getJobCategory()));
				} else {
					resourceCount.put(resource.getJobCategory(), 1);
				}
			}
			if (!resourceCount.isEmpty()) {
				response = new com.rummy.response.Response(this.converter(resourceCount), HttpStatus.OK, "success");
			}
		} catch (RAException e) {
			e.getMessage();
		}
		return response;
	}

	@Path("/totalResourceByTechnology")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public com.rummy.response.Response allResourceByTechnology(final @Context ServletContext context) {
		com.rummy.response.Response response = null;
		final Map<String, Integer> allResources = new ConcurrentHashMap<String, Integer>();
		final MongoSortVO sort = new MongoSortVO();
		try {
			sort.setPrimaryKey("_id");
			final List<Resource> allObjects = resourceService.getAllObjects(sort);
			for (final Resource resource : allObjects) {
				if (null != resource) {
					final String skill = resource.getPrimarySkills();
					final String[] skills = skill.split(",");
					for (final String str : skills) {
						if (allResources.containsKey(str)) {
							allResources.put(str, allResources.get(str) + 1);
						} else {
							allResources.put(str, 1);
						}
					}
				}
				if (!allResources.isEmpty()) {
					final List<ObjectMapper> list = converter(allResources);
					response = new com.rummy.response.Response(HttpStatus.OK, list, "succes");
				}
			}

		} catch (RAException e) {
			response = new com.rummy.response.Response(HttpStatus.CONFLICT, "No records found");
			throw new RAException();
		}
		return response;
	}

	/**
	 * 
	 * @param context
	 * @param vendorId
	 * @return
	 */
	@Path("/topTenCustomersByRequirements/{vendorId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public com.rummy.response.Response topTenCustomersByRequirements(@Context final ServletContext context,
			@PathParam("vendorId") final ObjectId vendorId) {
		com.rummy.response.Response response = null;
		List<ObjectMapper> mapperList = null;
		final MongoSortVO sort = new MongoSortVO();
		sort.setPrimaryKey("_id");
		sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);
		final Map<String, Integer> reverseSortedMap = new ConcurrentHashMap<String, Integer>();
		final Map<String, Integer> findTopCustomers = requirementService.findTopCustomers(sort, vendorId);
		final Set<Entry<String, Integer>> set = findTopCustomers.entrySet();
		final List<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(set);
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			@Override
			public int compare(final Map.Entry<String, Integer> o1, final Map.Entry<String, Integer> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		});
		for (final Map.Entry<String, Integer> entry : list) {
			final String key = entry.getKey();
			final Integer value = entry.getValue();
			reverseSortedMap.put(key, value);
			mapperList = converter(reverseSortedMap);
		}

		if (reverseSortedMap == null || reverseSortedMap.isEmpty()) {
			response = new com.rummy.response.Response(mapperList, HttpStatus.CONFLICT, "failed");
		} else {
			response = new com.rummy.response.Response(HttpStatus.OK, mapperList, "succes");
		}

		return response;
	}

	@Path("/topTenVendorsByResources")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public com.rummy.response.Response topTenVendorsByResources(@Context final ServletContext context) {
		com.rummy.response.Response response = null;
		List<ObjectMapper> mapperList = null;
		final MongoSortVO sort = new MongoSortVO();
		sort.setPrimaryKey("_id");
		sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);
		final Map<String, Integer> reverseSortedMap = new LinkedHashMap<String, Integer>();
		final Map<String, Integer> topTenVendors = resourceService.findTopVendors(sort);
		Set<Entry<String, Integer>> set = topTenVendors.entrySet();
		final List<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(set);
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			@Override
			public int compare(final Map.Entry<String, Integer> o1, final Map.Entry<String, Integer> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		});
		for (final Map.Entry<String, Integer> entry : list) {
			final String key = entry.getKey();
			final Integer value = entry.getValue();
			reverseSortedMap.put(key, value);
			mapperList = converter(reverseSortedMap);
		}

		if (reverseSortedMap == null || reverseSortedMap.isEmpty()) {
			response = new com.rummy.response.Response(mapperList, HttpStatus.CONFLICT, "failed");
		} else {
			response = new com.rummy.response.Response(mapperList, HttpStatus.OK, "succes");
		}

		return response;
	}

	/**
	 * 
	 * @param context
	 * @param vendorId
	 * @return
	 */
	@Path("/topTenVendorsByResources/{vendorId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public com.rummy.response.Response topTenVendorsByResources(@Context final ServletContext context,
			@PathParam("vendorId") final ObjectId vendorId) {  
		com.rummy.response.Response response = null;
		List<ObjectMapper> mapperList = null;
		final MongoSortVO sort = new MongoSortVO();
		sort.setPrimaryKey("_id");
		sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);
		final Map<String, Integer> reverseSortedMap = new ConcurrentHashMap<String, Integer>();
		final Map<String, Integer> topTenVendors = resourceService.findTopVendors(sort, vendorId);
		Set<Entry<String, Integer>> set = topTenVendors.entrySet();
		final List<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(set);
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			@Override
			public int compare(final Map.Entry<String, Integer> o1, final Map.Entry<String, Integer> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		});
		for (final Map.Entry<String, Integer> entry : list) {
			final String key = entry.getKey();
			final Integer value = entry.getValue();
			reverseSortedMap.put(key, value);
			mapperList = converter(reverseSortedMap);
		}

		if (reverseSortedMap == null || reverseSortedMap.isEmpty()) {
			response = new com.rummy.response.Response(mapperList, HttpStatus.CONFLICT, "failed");
		} else {
			response = new com.rummy.response.Response(HttpStatus.OK, mapperList, "succes");
		}

		return response;
	}	
	
	
	/**
	 * 
	 * @param resourceMapperList
	 * @param objectId
	 * @return
	 */
	// sow and companyName
	public List<ResourceMapper> addSowStatus(List<ResourceMapper> resourceMapperList, String objectId) {

		Registration regstration = null;
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("_id", new ObjectId(objectId));
		try {
			regstration = registrationService.findOneByCondition(condition);
		} catch (RAException e) {
			logger.error(e.getMessage());
			e.getMessage();
		}

		if (null != resourceMapperList) {
			for (ResourceMapper resourceMapper : resourceMapperList) {
				if (null != regstration && null != regstration.getSowUser()) {
					String[] sow = regstration.getSowUser().split(",");
					for (String regId : sow) {
						if (regId.equals(resourceMapper.getRegistrationId())) {
							logger.debug("setting SowUser status to Yes");
							resourceMapper.setIsSowUser("Yes");
						} else {
							logger.debug("setting SowUser status to No");
							resourceMapper.setIsSowUser("No");
						}
					}
				} else {
					logger.debug("setting SowUser status to No");
					resourceMapper.setIsSowUser("No");
				}
			}
		}
		return resourceMapperList;
	}

	/**
	 * 
	 * @param resourceList
	 * @return
	 */
	// All List New Method;
	public Map<String, Map<String, Integer>> allTLists(List<Resource> resourceList) {

		Map<String, Integer> skillsMap = new HashMap<String, Integer>();
		Map<String, Integer> yearsOfExperiencMap = new HashMap<String, Integer>();
		Map<String, Integer> jobCategoryMap = new HashMap<String, Integer>();
		Map<String, Integer> budgetMap = new HashMap<String, Integer>();
		Map<String, Integer> locationMap = new HashMap<String, Integer>();
		Map<String, Integer> vendorMap = new HashMap<String, Integer>();

		Map<String, Map<String, Integer>> allMap = new HashMap<String, Map<String, Integer>>();

		for (Resource resource : resourceList) {
			String[] primarySkills = resource.getPrimarySkills().split(",");
			for (String primaryskill : primarySkills) {
				if (skillsMap.containsKey(primaryskill)) {
					skillsMap.put(primaryskill, skillsMap.get(primaryskill) + 1);
				} else {
					if (null != primaryskill)
						skillsMap.put(primaryskill, 1);
				}
			}

			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("_id", resource.getRegistrationId());
			String compaanyName;

			compaanyName = registrationService.findOneByCondition(condition).getCompanyName();

			if (compaanyName != null) {
				if (vendorMap.containsKey(compaanyName)) {

					vendorMap.put(compaanyName, vendorMap.get(compaanyName) + 1);
				} else {
					if (null != compaanyName)
						vendorMap.put(compaanyName, 1);
				}
			}
			if (yearsOfExperiencMap.containsKey(resource.getYearsOfExperience())) {
				yearsOfExperiencMap.put(resource.getYearsOfExperience(),
						yearsOfExperiencMap.get(resource.getYearsOfExperience()) + 1);
			} else {
				if (null != resource.getYearsOfExperience())
					yearsOfExperiencMap.put(resource.getYearsOfExperience(), 1);
			}

			if (jobCategoryMap.containsKey(resource.getJobCategory())) {
				jobCategoryMap.put(resource.getJobCategory(), jobCategoryMap.get(resource.getJobCategory()) + 1);
			} else {
				if (null != resource.getJobCategory())
					jobCategoryMap.put(resource.getJobCategory(), 1);
			}

			if (budgetMap.containsKey(resource.getBudget())) {
				budgetMap.put(resource.getBudget(), budgetMap.get(resource.getBudget()) + 1);
			} else {
				if (null != resource.getBudget())
					budgetMap.put(resource.getBudget(), 1);
			}

			if (locationMap.containsKey(resource.getCurrentLocation())) {
				locationMap.put(resource.getCurrentLocation(), locationMap.get(resource.getCurrentLocation()) + 1);
			} else {
				if (null != resource.getCurrentLocation())
					locationMap.put(resource.getCurrentLocation(), 1);
			}
		}
		allMap.put("skillsMap", skillsMap);
		allMap.put("vedorMap", vendorMap);
		allMap.put("yearsOfExperiencMap", yearsOfExperiencMap);
		allMap.put("jobCategoryMap", jobCategoryMap);
		allMap.put("budgetMap", budgetMap);
		allMap.put("locationMap", locationMap);

		return allMap;
	}

	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> resourceByTechnologyAndCity(List<Resource> listResource) {

		// variable declaration
		Map<String, Integer> skillMap = new HashMap<String, Integer>();
		Map<String, Map<String, Integer>> cityMap = new HashMap<String, Map<String, Integer>>();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		// loopingtechnique
		for (Resource resource : listResource) {
			if (null != cityMap && cityMap.containsKey(resource.getCurrentLocation())) {
				skillMap = cityMap.get(resource.getCurrentLocation());
				String[] primaryskill = resource.getPrimarySkills().split(",");
				for (String ps : primaryskill) {

					if (skillMap.containsKey(ps)) {
						skillMap.put(ps, skillMap.get(ps) + 1);
					} else {
						skillMap.put(ps, 1);
					}
				}
				cityMap.put(resource.getCurrentLocation(), skillMap);
			} else {
				skillMap = new HashMap<String, Integer>();
				String[] primaryskill = resource.getPrimarySkills().split(",");
				for (String ps : primaryskill) {
					skillMap.put(ps, 1);
				}
				cityMap.put(resource.getCurrentLocation(), skillMap);
			}

		}

		Iterator<?> iterator = cityMap.entrySet().iterator();

		while (iterator.hasNext()) {

			Entry<?, ?> entry = (Entry<?, ?>) iterator.next();
			Map<String, Object> map = new HashMap<String, Object>();
			map = (Map<String, Object>) entry.getValue();
			map.put("city", entry.getKey());
			result.add(map);
		}
		return result;
	}

	private Set<String> skillSet(List<Resource> resourcelist) {

		Set<String> skillSet = new HashSet<String>();

		for (Resource resource : resourcelist) {

			if (null != resource.getPrimarySkills()) {
				String[] primarySkills = resource.getPrimarySkills().split(",");
				for (String primarySkill : primarySkills) {
					skillSet.add(primarySkill);
				}
			}

		}

		return skillSet;

	}

	/**
	 * 
	 * @param resources
	 * @return
	 */
	private List<ResourceMapper> convertDomainToMapperList(List<Resource> resources) {
		List<ResourceMapper> list = null;
		try {
			list = new ArrayList<ResourceMapper>();
			for (Resource resource : resources) {
				ResourceMapper resourceMapper = new ResourceMapper();
				BeanUtils.copyProperties(resource, resourceMapper);
				resourceMapper.set_id(resource.get_id().toString());
				if (null != resource.getRegistrationId()) {
					resourceMapper.setRegistrationId(resource.getRegistrationId().toString());
					Map<String, Object> condition = new HashMap<String, Object>();
					condition.put("_id", resource.getRegistrationId());
					Registration reg;
					try {
						reg = registrationService.findOneByCondition(condition);
						if (null != reg) {
							resourceMapper.setCompanyName(reg.getCompanyName());
							resourceMapper.setVendorEmailId(reg.getMailId());
							resourceMapper.setVendorMobile(reg.getMobile());
						} else
							throw new RAException("Data Not Found !!");
					} catch (RAException e) {
						logger.error("Data not found.." + e.getMessage());
						throw new RAException("Data Not Found !!");
					}
				}
				Map<String, Object> condition = new HashMap<String, Object>();
				condition.put("_id", resource.getRegistrationId());
				try {
					Registration reg = registrationService.findOneByCondition(condition);
					if (null != reg) {
						resourceMapper.setCompanyName(reg.getCompanyName());
						resourceMapper.setVendorEmailId(reg.getMailId());
						resourceMapper.setVendorMobile(reg.getMobile());
					} else
						throw new RAException("Data Not Found !!");
				} catch (RAException e) {
					logger.error("Data not found.." + e.getMessage());
					throw new RAException("Data Not Found !!");
				}
				list.add(resourceMapper);
			}
			return list;
		} catch (RAException e) {
			logger.error(e.getMessage());
			throw new RAException(e.getMessage());
		}
	}

	/**
	 * 
	 * @param resources
	 * @return
	 */
	private ResourceMapper convertDomainToMappar(Resource resources) {
		try {
			ResourceMapper resourceMapper = new ResourceMapper();
			BeanUtils.copyProperties(resources, resourceMapper);
			resourceMapper.set_id(resources.get_id().toString());
			if (null != resources.getRegistrationId()) {
				resourceMapper.setRegistrationId(resources.getRegistrationId().toString());
			}
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("_id", resources.getRegistrationId());
			try {
				Registration reg = registrationService.findOneByCondition(condition);
				if (null != reg) {
					resourceMapper.setCompanyName(reg.getCompanyName());
					resourceMapper.setVendorEmailId(reg.getMailId());
					resourceMapper.setVendorMobile(reg.getMobile());
				} else
					throw new RAException("Data Not Found !!");
				if (null != reg && reg.getSowUser() != null) {
					String[] sow = reg.getSowUser().split(",");
					for (String regId : sow) {
						if (regId.equals(resources.getRegistrationId().toString())) {
							logger.debug("set SowUser status to Yes");
							resourceMapper.setIsSowUser("Yes");
						} else {
							logger.debug("set SowUser status to No");
							resourceMapper.setIsSowUser("No");
						}
					}
				}
			} catch (RAException e) {
				logger.error("Data not found.." + e.getMessage());
				throw new RAException("Data Not Found !!");
			}
			return resourceMapper;
		} catch (RAException e) {
			logger.error(e.getMessage());
			throw new RAException(e.getMessage());
		}
	}

	/**
	 * 
	 * @return
	 */
	public String randomNumber() {
		UUID refIdGen = UUID.randomUUID();

		long hi = refIdGen.getMostSignificantBits();
		long lo = refIdGen.getLeastSignificantBits();

		byte[] bytes = ByteBuffer.allocate(16).putLong(hi).putLong(lo).array();

		BigInteger bigInteger = new BigInteger(bytes);
		String referenceNumber = bigInteger.toString();

		String refNumber = referenceNumber.substring(2, 7);
		return refNumber;
	}

	/**
	 * 
	 * @param objectId
	 * @param pgNo
	 * @param PgSize
	 * @return
	 */
	public List<ResourceMapper> optionList(String objectId, int pgNo, int PgSize) {
		MongoSortVO sort1 = new MongoSortVO();
		sort1.setPrimaryKey("registrationId");
		sort1.setPrimaryOrderBy(MongoOrderByEnum.DESC);
		List<ResourceMapper> mapperList1 = null;
		List<Resource> resourcelist1;
		try {
			MongoEqualQuery equal1 = new MongoEqualQuery();
			equal1.setEqualto("NO");
			MongoEqualQuery equal2 = new MongoEqualQuery();
			equal2.setEqualto("Active");
			Map<String, MongoAdvancedQuery> resourceMappingcondition = new HashMap<String, MongoAdvancedQuery>();
			resourceMappingcondition.put("hardLock", equal1);
			resourceMappingcondition.put("status", equal2);
			resourcelist1 = resourceService.advancedFindByCondition(resourceMappingcondition, sort1, pgNo, PgSize);
			mapperList1 = convertDomainToMapperList(resourcelist1);
		} catch (RAException e) {
			e.getMessage();
		}
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("_id", new ObjectId(objectId));
		Registration reg = null;
		try {
			reg = registrationService.findOneByCondition(condition);
		} catch (RAException e) {
			e.getMessage();
		}
		if (null != mapperList1) {
			for (ResourceMapper resourceMapper : mapperList1) {
				resourceMapper.setCompanyName(reg.getCompanyName());
				if (null != reg.getSowUser()) {
					String[] sow = reg.getSowUser().split(",");
					for (String regId : sow) {
						if (regId.equals(resourceMapper.getRegistrationId())) {
							logger.debug("set SowUser status to Yes");
							resourceMapper.setIsSowUser("Yes");
						} else {
							logger.debug("set SowUser status to No");
							resourceMapper.setIsSowUser("No");
						}
					}
				} else {
					logger.debug("set SowUser status to No");
					resourceMapper.setIsSowUser("No");
				}
			}
		}
		return mapperList1;
	}

	@Path("/findlocationbyvendorid/{regId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public com.rummy.response.Response resourceByJobLocation(@Context final ServletContext context,
			@PathParam("regId") final ObjectId regId) throws RAException {

		final MongoSortVO sort = new MongoSortVO();
		final Map<String, Integer> locationsCount = new ConcurrentHashMap<String, Integer>();
		final Map<String, Object> condition = new ConcurrentHashMap<String, Object>();
		condition.put("registrationId", regId);
		sort.setPrimaryKey("_id");
		sort.setPrimaryOrderBy(MongoOrderByEnum.ASC);
		com.rummy.response.Response response = new com.rummy.response.Response("No records found",
				HttpStatus.CONFLICT);

		try {
			List<Resource> resources = resourceService.findAllByCondition(condition, sort);
			for (final Resource resource : resources) {
				if (locationsCount.containsKey(resource.getCurrentLocation())) {
					locationsCount.put(resource.getCurrentLocation(),
							1 + locationsCount.get(resource.getCurrentLocation()));
				} else {
					locationsCount.put(resource.getCurrentLocation(), 1);
				}
			}
			if (!locationsCount.isEmpty()) {
				response = new com.rummy.response.Response(this.converter(locationsCount), HttpStatus.OK, "success");
			}
		} catch (RAException e) {
			throw new RAException();
		}
		return response;

	}

	/**
	 * 
	 * @return
	 */

	@Path("/findResourceByLocation")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public com.rummy.response.Response findByLoc() {
		final MongoSortVO sort = new MongoSortVO();
		sort.setPrimaryKey("_id");
		sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);

		List<Resource> requirementList = null;

		final Map<String, Integer> map = new ConcurrentHashMap<String, Integer>();
		requirementList = resourceService.getAllObjects(sort);

		for (final Resource resource : requirementList) {
			if (map.containsKey(resource.getCurrentLocation())) {
				map.put(resource.getCurrentLocation(), map.get(resource.getCurrentLocation()) + 1);
			} else {
				map.put(resource.getCurrentLocation(), 1);
			}

		}
		final List<ObjectMapper2> jobbyloc = new ArrayList<ObjectMapper2>();
		ObjectMapper2 obj = null;
		for (final Entry<String, Integer> entry : map.entrySet()) {
			obj = new ObjectMapper2(entry.getValue().toString(), entry.getKey());

			jobbyloc.add(obj);
		}
		if (jobbyloc.isEmpty()) {
			throw new RAException("no data found");
		}
		return new com.rummy.response.Response(jobbyloc, HttpStatus.OK, "success");

	}


	//business status of paposedresourced for vendor
	public List<ResourceMapper> vendorproposedStatus(List<ResourceMapper> resourceMapperList, String vendorId, String requirementId) {
		Map<String, String> condition = new HashMap<>();
		condition.put("vendorId", vendorId);
		List<ProposedResource> proposedResourceList = proposedResource.advancedFindByObjectIds(condition);

		for (ResourceMapper rm : resourceMapperList) {
			if (null != proposedResourceList && proposedResourceList.size() != 0) {
				for (ProposedResource proposedResource : proposedResourceList) {
					if (rm.get_id().equals(proposedResource.getResourceId().toString())&&requirementId.equals(proposedResource.getRequirementId())) {
						rm.setBusinessStatus("yes");
					} else {
						rm.setBusinessStatus("No");
					}
				}
			} else {
				rm.setBusinessStatus("No");
			}
		}
		return resourceMapperList;
	}


	// customer requested resource business status

	public List<ResourceMapper> requestdStatus(List<ResourceMapper> resourceMapperList, String customerId) {
		Map<String, String> condition = new HashMap<>();
		condition.put("registrationId", customerId);
		List<RequestResources> requestResourcesList = reqResourceService.advancedFindByObjectIds(condition);
		
		for (ResourceMapper rm : resourceMapperList) {
			if(null!=requestResourcesList&&requestResourcesList.size()!=0){
			for (RequestResources requestResource : requestResourcesList) {
				if (rm.get_id().equals(requestResource.getResourceId().toString())) {
					rm.setBusinessStatus("yes");
				}	else{
					rm.setBusinessStatus("No");
				}	
			}
	}else{
		rm.setBusinessStatus("No");
	}
		}
		return resourceMapperList;
	}

	public List<ObjectMapper> converter(final Map<String, Integer> locationsCount) {
		final List<ObjectMapper> objectMappers = new ArrayList<ObjectMapper>();
		ObjectMapper objectMapper = null;
		for (final Entry<String, Integer> entry : locationsCount.entrySet()) {

			objectMapper = new ObjectMapper(entry.getKey(), entry.getValue());
			objectMappers.add(objectMapper);
		}

		return objectMappers;
	}

	public int getPages(int count, int recordsPerPage) {
		int pages = 0;
		if (count != 0 && recordsPerPage != 0) {
			pages = count / recordsPerPage + (count % recordsPerPage);
		}
		return pages;

	}
	
	//vinay
	@GET
	@Path("/getAllResources/{vendorId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public com.rummy.response.Response getAllListsForSideFilter(@Context final ServletContext context,
	@PathParam("vendorId") final ObjectId vendorId){

	Map<String, Map<String, Integer>> allLists = null;
	try{


	List<Resource> vendorAllResourcesList=null;

	final MongoSortVO sort = new MongoSortVO();

	sort.setPrimaryKey("_id");
	sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);

	Map<String, Object> condition = new HashMap<String, Object>();
	condition.put("registrationId",vendorId);
	   
	   vendorAllResourcesList = resourceService.findAllByCondition(condition,sort);
	    
	     if (null != vendorAllResourcesList) {
	allLists = allTLists(vendorAllResourcesList);
	}
	     
	   }
	    catch(RAException ra){
	   	return new com.rummy.response.Response(HttpStatus.CONFLICT, "No records found");	
	      }
	   
	   
	   return new com.rummy.response.Response(allLists,HttpStatus.OK, "record found");

	}



	//vinay
	@Path("/getAllResources")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public com.rummy.response.Response getAllListsForSideFilter(@Context ServletContext context) {

		Map<String, Map<String, Integer>> allLists = null;
		try {

			MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryKey("_id");
			sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);
			List<Resource> list;

			try {

				list = resourceService.getAllObjects(sort);
				if (null != list) {
					allLists = allTLists(list);
				}

			} catch (RAException e) {

				return new com.rummy.response.Response(HttpStatus.NO_CONTENT, "No records found");
			}

			logger.debug("Records found");
			return new com.rummy.response.Response(allLists, HttpStatus.OK, "records found");
		} catch (RAException e) {
			logger.error("No Records found" + e.getMessage());
			return new com.rummy.response.Response(HttpStatus.CONFLICT, "records not found");
		}
	}
}
