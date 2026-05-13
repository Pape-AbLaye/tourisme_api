package tech.laye.tourisme_api.activity;

import aj.org.objectweb.asm.commons.Remapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ActivityRepository extends JpaRepository<Activity ,Long> {

    @Query("""
            SELECT activity
            FROM Activity activity
            WHERE activity.user.id = :user_id
            """)
    Page<Activity> findAllByUser( Pageable pageable , String user_id);

    Page<Activity> findByActivityType(Activity_type type, Pageable pageable);

    @Query("SELECT DISTINCT a FROM Activity a LEFT JOIN FETCH a.availabilities")
    Page<Activity> findAllWithAvailabilities(Pageable pageable);
}
