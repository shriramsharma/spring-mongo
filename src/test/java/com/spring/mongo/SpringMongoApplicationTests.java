package com.spring.mongo;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.spring.mongo.domain.RestaurantDO;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringMongoApplication.class)
@WebAppConfiguration
public class SpringMongoApplicationTests {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Test
	public void contextLoads() {
		assertNotNull(mongoTemplate);

		List<String> tags = new ArrayList<String>();
		tags.add("brunch");
		tags.add("indian");

		Point point = new Point(-117.313303, 33.182201);
		Distance distance = new Distance(50, Metrics.MILES);

		Query query = new Query();
		query.addCriteria(Criteria.where("tags").in(tags).and("location").nearSphere(point)
				.maxDistance(distance.getNormalizedValue()));
		List<RestaurantDO> restaurantDOs = mongoTemplate.find(query, RestaurantDO.class);

		assertNotNull(restaurantDOs);

	}

}
