package com.rummy.rest.resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import com.rummy.domain.Bonus;
import com.rummy.exception.RAException;
import com.rummy.mapper.BonusMapper;
import com.rummy.response.Response;
import com.rummy.services.BonusService;
import com.rummy.util.MongoOrderByEnum;
import com.rummy.util.MongoSortVO;

/**
 * 
 * @author skkhadar
 *
 */
@Component
@Path("/bonus")
public class BonusResource {

	@Autowired
	BonusService bonusService;

	Logger logger = Logger.getLogger(BonusResource.class);

	/**
	 * 
	 * @param bonusMapper
	 * @return
	 */
	@Path("/createBonus")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createBonus(@RequestBody BonusMapper bonusMapper) {
		try {
			Bonus bonus = new Bonus();
			BeanUtils.copyProperties(bonusMapper, bonus);
			bonus.setRegistrationId(new ObjectId(bonusMapper.getRegistrationId()));
			Boolean result = bonusService.createBonus(bonus);
			if (result == false) {
				logger.debug("Bonus not created");
				return new Response("Failed", result, HttpStatus.CONFLICT, "Bonus not created");
			}
			logger.debug("Bonus created successfully");
			return new Response("Success", result, HttpStatus.OK, "Bonus created");
		} catch (RAException e) {
			logger.error(e.getMessage());
			throw new RAException(e.getMessage());
		}
	}

	/**
	 * 
	 * @param BonusMapper
	 * @param id
	 * @return
	 */
	@Path("/saveBonus/{id}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveBonus(@RequestBody BonusMapper bonusMapper, @PathParam("id") ObjectId id) {
		try {
			Bonus bonus = new Bonus();
			BeanUtils.copyProperties(bonusMapper, bonus);
			bonus.set_id(id);
			bonus.setRegistrationId(new ObjectId(bonusMapper.getRegistrationId()));
			Boolean result = bonusService.saveBonus(bonus);
			if (result == false) {
				logger.debug("Bonus not saved for id " + id);
				return new Response("Failed", result, HttpStatus.CONFLICT, "Bonus not saved");
			}
			logger.debug("Bonus saved for id " + id);
			return new Response("Success", result, HttpStatus.OK, "Bonus saved");

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
	@Path("/listBonuss/{pageNo}/{pageSize}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response findAllByCondition(@Context ServletContext context, @PathParam("pageNo") int pageNo,
			@PathParam("pageSize") int pageSize) {
		try {
			MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryKey("_id");
			sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);
			List<Bonus> list;
			List<BonusMapper> mapperList;
			try {
				list = bonusService.getAllObjects(sort, pageNo, pageSize);
				mapperList = convertDomainToMapperList(list);
			} catch (RAException e) {
				logger.error("No records found " + e.getMessage());
				return new Response(HttpStatus.CONFLICT, "No records found");
			}
			int count = bonusService.getCount(sort);
			int pages = bonusService.getPages(sort, pageSize);
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
	 * @param context
	 * @param nameOftheProperty
	 * @param valueOftheProperty
	 * @return
	 */
	@Path("/findBonusByCondition/{nameOftheProperty}/{valueOftheProperty}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response findBonusByCondition(@Context ServletContext context,
			@PathParam("nameOftheProperty") String nameOftheProperty,
			@PathParam("valueOftheProperty") String valueOftheProperty) {
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put(nameOftheProperty, valueOftheProperty);
			Bonus bonus;
			BonusMapper bonusMapper;
			try {
				bonus = bonusService.findOneByCondition(condition);
				bonusMapper = convertDomainToMappar(bonus);
			} catch (RAException e) {
				logger.error("No records found " + e.getMessage());
				return new Response(HttpStatus.CONFLICT, "No records found");
			}
			if (bonusMapper == null) {
				logger.debug("No records found for nameOftheProperty " + nameOftheProperty + "and valueOftheProperty "
						+ valueOftheProperty);
				return new Response("Failed", bonusMapper, HttpStatus.CONFLICT, "No record found");
			}
			logger.debug("Records found for nameOftheProperty " + nameOftheProperty + "and valueOftheProperty "
					+ valueOftheProperty);
			return new Response("Success", bonusMapper, HttpStatus.OK, "record found");
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
	@Path("/findBonusByPrimaryId/{value}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response findBonusByPrimaryId(@Context ServletContext context, @PathParam("value") ObjectId value)
			throws JsonGenerationException, JsonMappingException, IOException {
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("_id", value);
			Bonus bonuss;
			BonusMapper bonus;
			try {
				bonuss = bonusService.findOneByCondition(condition);
				bonus = convertDomainToMappar(bonuss);
			} catch (RAException e) {
				logger.error("No records found " + e.getMessage());
				return new Response(HttpStatus.CONFLICT, "No records found");
			}
			if (bonus == null) {
				logger.debug("No records found for ObjectId " + value);
				return new Response("Failed", bonus, HttpStatus.CONFLICT, "No record found");
			}
			logger.debug("Records found successfully for ObjectId " + value);
			return new Response("Success", bonus, HttpStatus.OK, "record found");
		} catch (RAException e) {
			logger.error(e.getMessage());
			throw new RAException(e.getMessage());
		}
	}

	/**
	 * 
	 * @param Bonuss
	 * @return
	 */
	private List<BonusMapper> convertDomainToMapperList(List<Bonus> bonuss) {
		try {
			List<BonusMapper> list = new ArrayList<BonusMapper>();
			for (Bonus bonus : bonuss) {
				BonusMapper bonusMapper = new BonusMapper();
				BeanUtils.copyProperties(bonus, bonusMapper);
				bonusMapper.set_id(bonus.get_id().toString());
				list.add(bonusMapper);
			}
			logger.debug("Converting BonusList to BonusMapperList");
			return list;
		} catch (RAException e) {
			logger.error(e.getMessage());
			throw new RAException(e.getMessage());
		}
	}

	/**
	 * 
	 * @param Bonus
	 * @return
	 */
	private BonusMapper convertDomainToMappar(Bonus bonus) {
		try {
			BonusMapper bonusMapper = new BonusMapper();
			BeanUtils.copyProperties(bonus, bonusMapper);
			bonusMapper.set_id(bonus.get_id().toString());
			logger.debug("Converting Bonus to BonusMapper");
			return bonusMapper;
		} catch (RAException e) {
			logger.error(e.getMessage());
			throw new RAException(e.getMessage());
		}
	}
}
