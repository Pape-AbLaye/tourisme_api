package tech.laye.tourisme_api.accommodation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {

    @Query("""
            SELECT acc
            FROM Accommodation acc
            WHERE acc.user.id = :userId
            """)
    Page<Accommodation> findAllByUser(Pageable pageable, String userId);


}
