package net.engineeringdigest.journalapp.repository.user;

import net.engineeringdigest.journalapp.entity.UserEntity;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserEntityForSARepository {
    MongoTemplate mongoTemplate;

    public UserEntityForSARepository(MongoTemplate mongoTemplate){
        this.mongoTemplate = mongoTemplate;
    }

    public List<UserEntity> getAllUsersForSA(){
        Query query = new Query();
        Criteria criteria = new Criteria();

        query.addCriteria(criteria.andOperator(
                Criteria.where("optForSA").is(true),
                Criteria.where("email").regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,63}$")
        ));

        return mongoTemplate.find(query, UserEntity.class);
    }
}
