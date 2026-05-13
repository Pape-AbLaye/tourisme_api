package tech.laye.tourisme_api.availability;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AvailabilityRepository extends JpaRepository<Availability ,Long> {
    @Query("""
            SELECT av
            FROM Availability av
            WHERE av.user.id = :currentUserId
            """)
    Page<Availability> findAllByUser(Pageable pageable, String currentUserId);
}
