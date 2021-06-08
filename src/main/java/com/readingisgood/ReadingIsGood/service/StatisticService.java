package com.readingisgood.ReadingIsGood.service;

import com.readingisgood.ReadingIsGood.dao.OrderEntity;
import com.readingisgood.ReadingIsGood.dao.OrderRepository;
import com.readingisgood.ReadingIsGood.dao.StatisticEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Service
public class StatisticService {

    @PersistenceContext
    private EntityManager em;

    public List<StatisticEntity> getStatisticByCustomerId(Long customerId) {
        log.info("getStatisticByCustomerId", customerId);
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root orders = cq.from(OrderEntity.class);
        Expression<Integer> month = cb.function("month", Integer.class, orders.get("createDate"));
        Predicate customerIdPredicate = cb.equal(orders.get("customerId"), customerId);
        cq.select(cb.construct(StatisticEntity.class, month,  cb.count(orders), cb.sum(orders.<Number>get("piece")), cb.sum(orders.<Number>get("amount"))));
        cq.where(customerIdPredicate);
        cq.groupBy(month);
        Query query = em.createQuery(cq);
        List<StatisticEntity> results = query.getResultList();
        return results;
    }
}
