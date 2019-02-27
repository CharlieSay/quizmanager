package co.uk.webbiskools.quizmanager.dao;

import co.uk.webbiskools.quizmanager.resource.AuthorisationToken;

import java.util.Optional;

public class Authorisation {

    private Optional<Integer> groupId;
    private Optional<Integer> userId;
    private AuthorisationToken authorisationToken;

    public Authorisation(Optional<Integer> groupId, Optional<Integer> userId, AuthorisationToken authorisationToken) {
        this.authorisationToken = authorisationToken;
        if (groupId.isPresent()){
            this.groupId = groupId;
        }
        if (userId.isPresent()){
            this.userId = userId;
        }
        this.groupId = groupId;
    }

    public Optional<Integer> getGroupId() {
        return groupId;
    }

    public Optional<Integer> getUserId() {
        return userId;
    }

    public AuthorisationToken getAuthorisationToken() {
        return authorisationToken;
    }
}