package co.uk.webbiskools.quizmanager.dao;

import co.uk.webbiskools.quizmanager.resource.AuthorisationToken;

import java.util.Optional;

public class Authorisation {

    private Optional<Integer> groupId;
    private AuthorisationToken authorisationToken;

    public Authorisation(Optional<Integer> groupId, AuthorisationToken authorisationToken) {
        this.authorisationToken = authorisationToken;
        if (groupId.isPresent()){
            this.groupId = groupId;
        }
        this.groupId = groupId;
    }

    public Optional<Integer> getGroupId() {
        return groupId;
    }

    public AuthorisationToken getAuthorisationToken() {
        return authorisationToken;
    }
}