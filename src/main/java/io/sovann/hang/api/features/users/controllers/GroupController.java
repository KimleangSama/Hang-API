package io.sovann.hang.api.features.users.controllers;

import io.sovann.hang.api.annotations.*;
import io.sovann.hang.api.constants.*;
import io.sovann.hang.api.features.commons.controllers.*;
import io.sovann.hang.api.features.commons.payloads.*;
import io.sovann.hang.api.features.users.payloads.request.*;
import io.sovann.hang.api.features.users.payloads.response.*;
import io.sovann.hang.api.features.users.securities.*;
import io.sovann.hang.api.features.users.services.*;
import io.sovann.hang.api.utils.*;
import java.util.*;
import lombok.*;
import org.springframework.security.access.prepost.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(APIURLs.GROUP)
@RequiredArgsConstructor
public class GroupController {
    private final GroupServiceImpl groupService;
    private final ControllerServiceCallback callback;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('admin', 'manager')")
    public BaseResponse<GroupResponse> createGroup(
            @CurrentUser CustomUserDetails user,
            @RequestBody CreateGroupRequest request
    ) {
        SoftEntityDeletable.throwErrorIfSoftDeleted(user.getUser());
        return callback.execute(() -> groupService.createGroup(user.getUser(), request),
                "Group failed to create",
                null);
    }

    @GetMapping("/{id}/users")
    @PreAuthorize("hasAnyRole('admin', 'manager')")
    public BaseResponse<List<UserResponse>> getUsers(
            @CurrentUser CustomUserDetails user,
            @PathVariable UUID id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        SoftEntityDeletable.throwErrorIfSoftDeleted(user.getUser());
        PageMeta pageMeta = new PageMeta(page, size, groupService.countUsers(id));
        return callback.execute(() -> groupService.getUsers(user.getUser(), id, page, size),
                "Groups failed to fetch",
                pageMeta);
    }

    @DeleteMapping("/remove")
    @PreAuthorize("hasAnyRole('admin', 'manager')")
    public BaseResponse<GroupMemberResponse> removeUser(
            @CurrentUser CustomUserDetails user,
            @RequestBody AddOrRemoveGroupMemberRequest request
    ) {
        SoftEntityDeletable.throwErrorIfSoftDeleted(user.getUser());
        return callback.execute(() -> groupService.removeUser(user.getUser(), request),
                "Failed to remove user from group",
                null);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('admin', 'manager')")
    public BaseResponse<GroupMemberResponse> addUser(
            @CurrentUser CustomUserDetails user,
            @RequestBody AddOrRemoveGroupMemberRequest request
    ) {
        SoftEntityDeletable.throwErrorIfSoftDeleted(user.getUser());
        return callback.execute(() -> groupService.addUser(user.getUser(), request),
                "Failed to add user to group",
                null);
    }

    @PostMapping("/mote")
    @PreAuthorize("hasAnyRole('admin', 'manager')")
    public BaseResponse<GroupResponse> promoteUser(
            @CurrentUser CustomUserDetails user,
            @RequestBody PromoteDemoteRequest request
    ) {
        SoftEntityDeletable.throwErrorIfSoftDeleted(user.getUser());
        return callback.execute(() -> groupService.promoteOrDemoteUser(user.getUser(), request),
                "User failed to promote",
                null);
    }

    @PostMapping("/register")
    @PreAuthorize("hasAnyRole('admin', 'manager')")
    public BaseResponse<GroupMemberResponse> registerUser(
            @CurrentUser CustomUserDetails user,
            @RequestBody RegisterToGroupRequest request
    ) {
        SoftEntityDeletable.throwErrorIfSoftDeleted(user.getUser());
        return callback.execute(() -> groupService.registerUser(user.getUser(), request),
                "Failed to register user to group",
                null);
    }
}
