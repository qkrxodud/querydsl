package study.querydsl.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import study.querydsl.entitiy.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static study.querydsl.entitiy.QMember.member;

@Repository
@RequiredArgsConstructor
public class MemberJpaRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public void save(Member member) {
        em.persist(member);
    }

    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    public List<Member> findAll() {
        return em.createQuery("select m From Member m ", Member.class)
                .getResultList();
    }

    public List<Member> findAll_querydsl() {
        return queryFactory.select(member)
                .from(member)
                .fetch();
    }

    public List<Member> findUsername_querydsl(String username) {
        return queryFactory.select(member)
                .from(member)
                .where(member.username.eq(username))
                .fetch();
    }

    public List<Member> findByUsername(String username) {
        return em.createQuery("select m From Member m where m.username = :username", Member.class)
                .setParameter("username", username)
                .getResultList();
    }


}
