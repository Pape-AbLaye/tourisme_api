package tech.laye.tourisme_api.activity;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.naming.OperationNotSupportedException;

@RestController
@RequestMapping("/activity")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;


    @GetMapping
    public ResponseEntity<Page<ActivityResponse>> getAllActivity(
            @RequestParam(name = "page", defaultValue = "0" , required = false) int page,
            @RequestParam(name = "size", defaultValue = "10" , required = false)int size
    ){
        return ResponseEntity.ok(activityService.getAllActivity(page,size));
    }

    @GetMapping("/owner")
    public ResponseEntity<Page<ActivityResponse>> getMyActivity(
            @RequestParam(name = "page", defaultValue = "0" , required = false) int page,
            @RequestParam(name = "size", defaultValue = "10" , required = false)int size,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(activityService.getMyActivity(page,size,connectedUser));
    }

    @GetMapping("/{id}")
    public ActivityResponse getActivity(@PathVariable Long id){
        return activityService.getActivity(id);
    }

    @GetMapping("/type/{type}")
    public Page<ActivityResponse> getActivityByType(
            @RequestParam(name = "page", defaultValue = "0" , required = false) int page,
            @RequestParam(name = "size", defaultValue = "10" , required = false)int size,
            @PathVariable Activity_type type
    ){
        return activityService.getActivityByType(page,size,type);
    }

    @PostMapping
    public ResponseEntity<Long> saveActivity(
            @RequestBody @Valid ActivityRequest activityRequest,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(this.activityService.save(activityRequest,connectedUser));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Long> updateActivity(
            @PathVariable Long id ,
            @RequestBody @Valid ActivityRequest activityRequest,
            Authentication connectedUser
    ) throws OperationNotSupportedException {
        return ResponseEntity.ok(activityService.updateActivity(id,activityRequest ,connectedUser));
    }

    @DeleteMapping("/{id}")
    public void deleteActivity(
            @PathVariable Long id ,
            Authentication connectedUser
    ) throws OperationNotSupportedException {
        activityService.deleteActivity(id,connectedUser);
    }
}
