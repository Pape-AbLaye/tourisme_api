package tech.laye.tourisme_api.circuit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CircuitRepository extends JpaRepository<Circuit , Long> {

    @Query("""
            SELECT c
            FROM Circuit c
            WHERE c.user.id = :user_id
            """)
    Page<Circuit> findAllByUser(Pageable pageable, String user_id);
}
