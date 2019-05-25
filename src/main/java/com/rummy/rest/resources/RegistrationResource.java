package com.rummy.rest.resources;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import com.rummy.domain.CashLimit;
import com.rummy.domain.Registration;
import com.rummy.domain.Role;
import com.rummy.domain.RoleMapping;
import com.rummy.domain.UserAccount;
import com.rummy.exception.RAException;
import com.rummy.mapper.RegistrationMapper;
import com.rummy.response.Response;
import com.rummy.services.CashLimitService;
import com.rummy.services.RegistrationService;
import com.rummy.services.RoleMappingService;
import com.rummy.services.RoleService;
import com.rummy.services.UserService;
import com.rummy.util.MongoOrderByEnum;
import com.rummy.util.MongoSortVO;
import com.rummy.util.SendMail;

/**
 * 
 * @author skkhadar
 *
 */
@Component
@Path("/registration")
public class RegistrationResource {

	@Autowired
	private RegistrationService registrationService;
	@Autowired
	UserService userService;

	@Autowired
	RoleService roleService;

	@Autowired
	CashLimitService cashLimitService;
	@Autowired
	RoleMappingService roleMappingService;
	@Autowired
	SendMail sendMail;

	Logger logger = Logger.getLogger(RegistrationResource.class);

	public static String SAVE_FOLDER;

	/**
	 * 
	 * @param registration
	 * @return
	 */
	@SuppressWarnings("static-access")
	@POST
	@Path("/createRegistration")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createResgister(@RequestBody RegistrationMapper registration) {
		boolean result = false;
		Registration reg = new Registration();
		BeanUtils.copyProperties(registration, reg);
		try {
			UserAccount accounts;
			try {
				accounts = userService.loadUserByUsername(registration.getMailId());
			} catch (RAException e) {
				logger.error("Registration not created " + e.getMessage());
				return new Response(HttpStatus.CONFLICT, "User already exists");
			}
			if (null == accounts) {
				reg.setStatus("Active");
				// setting dates
				Calendar calender = Calendar.getInstance();
				reg.setRegisteredDate(calender.getTime());
				calender.setTime(calender.getTime());
				calender.add(calender.DATE, 90);
				result = registrationService.create(reg);
				Map<String, Object> condition = new HashMap<String, Object>();
				if (null != registration.getMailId())
					condition.put("mailId", registration.getMailId());
				else
					condition.put("mobile", registration.getMobile());
				Registration regs;
				try {
					regs = registrationService.findOneByCondition(condition);
				} catch (RAException e) {
					logger.error("Registration not created " + e.getMessage());
					return new Response(HttpStatus.CONFLICT, "No records found");
				}
				if (result) {
					UserAccount userAccount = new UserAccount();
					userAccount.setUsername(registration.getUsername());
					MessageDigest md;
					try {
						md = MessageDigest.getInstance("MD5");
						byte[] messageDigest = md.digest(registration.getPassword().getBytes());
						BigInteger number = new BigInteger(1, messageDigest);
						String hashtext = number.toString(16);
						userAccount.setPassword(hashtext);
						userAccount.setRegistrationId(regs.get_id());
						userAccount.setStatus("active");
						userAccount.setPromEmails("yes");
						userAccount.setPromMsgs("yes");
						userService.save(userAccount);
						UserAccount account;
						try {
							account = userService.loadUserByUsername(userAccount.getUsername());
							condition.put("username", account.getUsername());
						} catch (RAException e) {
							logger.error("No records found " + e.getMessage());
							return new Response(HttpStatus.CONFLICT, "No records found");
						}
						Map<String, Object> rolecondition = new HashMap<String, Object>();

						rolecondition.put("roleName", "USER");
						Role role;
						try {
							role = roleService.findOneByCondition(rolecondition);
						} catch (RAException e) {
							logger.error("No records found " + e.getMessage());
							return new Response(HttpStatus.CONFLICT, "No records found");
						}
						RoleMapping mapping = new RoleMapping();
						mapping.setRole_id(role.get_id());
						mapping.setUser_id(account.get_id());
						roleMappingService.createRoleMapping(mapping);

						CashLimit cashLimit = new CashLimit();
						cashLimit.setRegistrationId(reg.get_id());
						cashLimit.setCurrentDailyLimit("1000");
						cashLimit.setCurrentMonthlyLimit("30000");
						cashLimit.setStatus("InActive");
						cashLimit.setDate(new Date());
						String subject = userAccount.getUsername()
								+ "activate your account to get your special Welcome Bonus!";
						String msg = "";
						sendMail.sendMail(registration.getMailId(), subject, msg);

					} catch (NoSuchAlgorithmException e) {
						logger.error(e.getMessage());
						throw new RAException(e.getMessage());
					}
				}
			} else {
				return new Response("Already Registerd with given Email Id " + accounts.getUsername());
			}
			if (result == false) {
				logger.debug("Registration not created");
				return new Response("Failed", result, HttpStatus.CONFLICT, "not created");
			}
			logger.debug("Registration created");
			return new Response("Success", result, HttpStatus.OK, "created");
		} catch (RAException e) {
			logger.error(e.getMessage());
			throw new RAException(e.getMessage());
		}
	}

	/**
	 * 
	 * @param uploadFile
	 * @param vendorid
	 * @param resourceid
	 * @return
	 * @throws IOException
	 */

	@POST
	@Path("/upload/{vendorid}/{resourceid}/{name}/{jobcategory}/{exp}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response uploadFile(@FormDataParam("file") InputStream fileInputString,
			@FormDataParam("file") FormDataContentDisposition fileInputDetails, @PathParam("vendorid") String vendorid,
			@PathParam("resourceid") String resourceid, @PathParam("name") String name,
			@PathParam("jobcategory") String jobcategory, @PathParam("exp") String exp) {

		SAVE_FOLDER = "D://RAresumes/" + vendorid + "_" + resourceid + "_" + name + "_" + jobcategory + "_" + exp + "_";
		String fileLocation = SAVE_FOLDER + fileInputDetails.getFileName();
		String status = null;
		NumberFormat myFormat = NumberFormat.getInstance();
		myFormat.setGroupingUsed(true);

		// Save the file
		try {
			OutputStream out = new FileOutputStream(new File(fileLocation));
			byte[] buffer = new byte[1024];
			int bytes = 0;
			long file_size = 0;
			while ((bytes = fileInputString.read(buffer)) != -1) {
				out.write(buffer, 0, bytes);
				file_size += bytes;
			}
			out.flush();
			out.close();

			logger.info(String.format("Inside uploadFile==> fileName: %s,  fileSize: %s",
					fileInputDetails.getFileName(), myFormat.format(file_size)));

			status = "File has been uploaded to:" + fileLocation + ", size: " + myFormat.format(file_size) + " bytes";
		} catch (IOException ex) {
			logger.error("Unable to save file: " + fileLocation);
			return new Response(status, HttpStatus.CONFLICT, "notuploaded");
		}

		return new Response(status, HttpStatus.OK, "uploaded");
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
	public Response uploadFile(@FormDataParam("uploadFile") InputStream uploadFile,
			@PathParam("idProofNo") String idProofNo, @PathParam("idProofType") String idProofType,
			@PathParam("id") ObjectId id) throws IOException {
		try {
			Registration registration = new Registration();
			registration.set_id(id);
			registration.setFile(IOUtils.toByteArray(uploadFile));
			registration.setIdProofType(idProofType);
			registration.setIdProofNo(idProofNo);
			Boolean result = registrationService.save(registration);
			if (result == false) {
				logger.debug("file not uploaded for id " + id);
				return new Response("Failed", result, HttpStatus.CONFLICT, "not uploaded");
			}
			logger.debug("file uploaded for id " + id);
			return new Response("Success", result, HttpStatus.OK, "uploaded");

		} catch (RAException e) {
			logger.error(e.getMessage());
			throw new RAException(e.getMessage());
		}
	}

	/**
	 * 
	 * @param registrationMapper
	 * @param id
	 * @return
	 */
	@PUT
	@Path("/saveRegistration/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveRegister(@RequestBody RegistrationMapper registrationMapper, @PathParam("id") ObjectId id) {
		try {
			Registration registration = new Registration();
			BeanUtils.copyProperties(registrationMapper, registration);
			registration.set_id(id);
			registration.setRegisteredDate(registration.getRegisteredDate());
			Boolean result = registrationService.save(registration);
			if (result == false) {
				logger.debug("Registration not saved for id " + id);
				return new Response("Failed", result, HttpStatus.CONFLICT, "not saved");
			}
			logger.debug("Registration saved for id " + id);
			return new Response("Success", result, HttpStatus.OK, "created");

		} catch (RAException e) {
			logger.error(e.getMessage());
			throw new RAException(e.getMessage());
		}
	}

	/**
	 * 
	 * @param context
	 * @param id
	 * @return
	 */
	@DELETE
	@Path("/deleteRegistration/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteRegisterById(@Context ServletContext context, @PathParam("id") ObjectId id) {
		try {
			Boolean result = registrationService.delete(id);
			if (result == false) {
				logger.debug("Registration not deleted for id " + id);
				return new Response("Failed", result, HttpStatus.CONFLICT, "not deleted");
			}
			logger.debug("Registration deleted successfully for id " + id);
			return new Response("Success", result, HttpStatus.OK, "deleted");
		} catch (RAException e) {
			logger.error(e.getMessage());
			throw new RAException(e.getMessage());
		}
	}

	/**
	 * 
	 * @param context
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@Path("/listRegistrations/{pageNo}/{pageSize}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response findAllByCondition(@Context ServletContext context, @PathParam("pageNo") int pageNo,
			@PathParam("pageSize") int pageSize) {
		try {
			MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryKey("_id");
			sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);
			List<Registration> list;
			List<RegistrationMapper> mapperList;
			try {
				list = registrationService.getAllObjects(sort, pageNo, pageSize);
				mapperList = convertDomainToMapperList(list);
			} catch (RAException e) {
				logger.error("No records found " + e.getMessage());
				return new Response(HttpStatus.CONFLICT, "No records found");
			}
			int count = registrationService.getCount(sort);
			int pages = registrationService.getPages(sort, pageSize);
			if (mapperList == null || mapperList.size() == 0) {
				logger.debug("No records found ");
				return new Response(mapperList, pages, count, HttpStatus.CONFLICT, "No records found");
			}
			logger.debug("Records found successfully");
			return new Response(mapperList, pages, count, HttpStatus.OK, "records found");
		} catch (RAException e) {
			logger.error(e.getMessage());
			throw new RAException(e.getMessage());
		}
	}

	/**
	 * 
	 * @param context
	 * @return
	 */
	@Path("/listRegistrations/{registrationType}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response findAllByCondition(@PathParam("registrationType") String registrationType) {
		List<Registration> actual = new CopyOnWriteArrayList<Registration>();
		try {
			MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryKey("_id");
			sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);
			List<Registration> list;
			List<RegistrationMapper> mapperList;
			try {
				list = registrationService.getAllObjects(sort);
				actual.addAll(list);
				Iterator<Registration> iterator = actual.iterator();

				while (iterator.hasNext()) {
					Registration registration = iterator.next();
					/*
					 * if (registration.getRegistrationType() != null ||
					 * !(registration.getRegistrationType()).isEmpty()) { if
					 * (registration.getRegistrationType().equals(
					 * registrationType)) { actual.remove(registration);
					 * 
					 * } }
					 */
				}
				mapperList = convertDomainToMapperList(actual);
			} catch (RAException e) {
				logger.error("No records found " + e.getMessage());
				return new Response(HttpStatus.CONFLICT, "No records found");
			}
			if (mapperList == null || mapperList.size() == 0) {
				logger.debug("No records found ");
				return new Response("Failed", mapperList, HttpStatus.CONFLICT, "No records found");
			}
			logger.debug("Records found successfully");
			return new Response("Success", mapperList, HttpStatus.OK, "records found");
		} catch (RAException e) {
			logger.error(e.getMessage());
			throw new RAException(e.getMessage());
		}
	}

	/**
	 * 
	 * @param context
	 * @param id
	 * @return
	 */
	@Path("/removeByPrimaryId/{id}")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response removeByPrimaryId(@Context ServletContext context, @PathParam("id") String id) {
		try {
			Boolean result = registrationService.removeByPrimaryId(id);
			if (result == false) {
				logger.debug("Registration not deleted for id " + id);
				return new Response("Failed", result, HttpStatus.CONFLICT, "not deleted");
			}
			logger.debug("Registration deleted for id " + id);
			return new Response("Success", result, HttpStatus.OK, "deleted");

		} catch (RAException e) {
			logger.error(e.getMessage());
			throw new RAException(e.getMessage());
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
	public Response findOneByCondition(@Context ServletContext context,
			@PathParam("nameOftheProperty") String nameOftheProperty,
			@PathParam("valueOftheProperty") String valueOftheProperty) {
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put(nameOftheProperty, valueOftheProperty);
			Registration registration;
			RegistrationMapper mapper;
			try {
				registration = registrationService.findOneByCondition(condition);
				mapper = convertDomainToMappar(registration);
			} catch (RAException e) {
				logger.error("No records found " + e.getMessage());
				return new Response(HttpStatus.CONFLICT, "No records found");
			}
			if (mapper == null) {
				logger.debug("No records found");
				return new Response("Failed", mapper, HttpStatus.CONFLICT, "No record found");
			}
			logger.debug("Records found successfully");
			return new Response("Success", mapper, HttpStatus.OK, "record found");
		} catch (RAException e) {
			logger.error(e.getMessage());
			throw new RAException(e.getMessage());
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
	public Response findOneByPrimaryId(@Context ServletContext context, @PathParam("value") ObjectId value)
			throws JsonGenerationException, JsonMappingException, IOException {
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("_id", value);
			Registration reg;
			RegistrationMapper mapper;
			try {
				reg = registrationService.findOneByCondition(condition);
				mapper = convertDomainToMappar(reg);
			} catch (RAException e) {
				logger.error("No records found " + e.getMessage());
				return new Response(HttpStatus.CONFLICT, "No records found");
			}
			if (mapper == null) {
				logger.debug("No records found");
				return new Response("Failed", mapper, HttpStatus.CONFLICT, "No record found");
			}
			logger.debug("Records found successfully");
			return new Response("Success", mapper, HttpStatus.OK, "record found");
		} catch (RAException e) {
			logger.error(e.getMessage());
			throw new RAException(e.getMessage());
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
	public Response findOneAllByCondition(@Context ServletContext context,
			@PathParam("nameOftheProperty") String nameOftheProperty,
			@PathParam("valueOftheProperty") String valueOftheProperty, @PathParam("pageNo") int pageNo,
			@PathParam("pageSize") int pageSize) {
		try {
			MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryKey("_id");
			sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);
			List<Registration> reglist;
			List<RegistrationMapper> mapperList;
			try {
				reglist = registrationService.advancedFindByCondition(nameOftheProperty, valueOftheProperty, sort,
						pageNo, pageSize);
				mapperList = convertDomainToMapperList(reglist);
			} catch (RAException e) {
				logger.error("No records found " + e.getMessage());
				return new Response(HttpStatus.CONFLICT, "No records found");
			}
			int count = registrationService.getCount(sort);
			int pages = registrationService.getPages(sort, pageSize);
			if (mapperList == null || mapperList.size() == 0) {
				logger.debug("No records found");
				return new Response(mapperList, pages, count, HttpStatus.CONFLICT, "No records found");
			}
			logger.debug("Records found successfully");
			return new Response(mapperList, pages, count, HttpStatus.OK, "records found");
		} catch (RAException e) {
			logger.error(e.getMessage());
			throw new RAException(e.getMessage());
		}
	}

	/**
	 * 
	 * @param registrationMapper
	 * @param id
	 * @return
	 */
	@PUT
	@Path("/inactiveOrActive/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response inactiveOrActive(@RequestBody RegistrationMapper registrationMapper, @PathParam("id") ObjectId id) {
		try {
			Registration registration = new Registration();
			BeanUtils.copyProperties(registrationMapper, registration);
			registration.set_id(id);
			registration.setRegisteredDate(registration.getRegisteredDate());
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("_id", id);
			Map<String, Object> target = new HashMap<String, Object>();
			target.put("status", registration.getStatus());

			Map<String, Object> condition1 = new HashMap<String, Object>();
			condition1.put("registrationId", id);
			Map<String, Object> target1 = new HashMap<String, Object>();

			target1.put("status", registration.getStatus());
			userService.updateMapByCondition(condition1, target1);

			target.put("status", registration.getStatus());
			Boolean result = registrationService.updateMapByCondition(condition, target);
			if (result == false) {
				logger.debug("registration inactive");
				return new Response("Failed", HttpStatus.CONFLICT);
			}
			logger.debug("registration active");
			return new Response("Success", HttpStatus.OK);

		} catch (RAException e) {
			logger.error(e.getMessage());
			throw new RAException(e.getMessage());
		}
	}

	/**
	 * 
	 * @param registrations
	 * @return
	 */
	private List<RegistrationMapper> convertDomainToMapperList(List<Registration> registrations) {
		try {
			List<RegistrationMapper> list = new ArrayList<RegistrationMapper>();
			for (Registration registration : registrations) {
				RegistrationMapper registrationMapper = new RegistrationMapper();
				BeanUtils.copyProperties(registration, registrationMapper);
				registrationMapper.set_id(registration.get_id().toString());
				list.add(registrationMapper);
			}
			logger.debug("Converting RegistrationList to RegistrationMapperList");
			return list;
		} catch (RAException e) {
			logger.error(e.getMessage());
			throw new RAException(e.getMessage());
		}
	}

	/**
	 * 
	 * @param registration
	 * @return
	 */
	private RegistrationMapper convertDomainToMappar(Registration registration) {
		try {
			RegistrationMapper registrationMapper = new RegistrationMapper();
			BeanUtils.copyProperties(registration, registrationMapper);
			registrationMapper.set_id(registration.get_id().toString());
			logger.debug("Converting Registration to RegistrationMapper");
			return registrationMapper;
		} catch (RAException e) {
			logger.error(e.getMessage());
			throw new RAException(e.getMessage());
		}
	}
}