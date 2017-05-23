package com.ebiz.cdb.persistence.dao;

import com.ebiz.cdb.core.models.User;

import java.util.List;

public interface UserDao {

    /**
     * Get the list of all the existing users on the db.
     *
     * @return the list of users
     */
    List<User> getAll();

    /**
     * Returns a user according to username and password.
     *
     * @param username of the user to retrieve
     * @return retrieved user
     */
    User get(String username);

    /**
     * Create a new user.
     *
     * @param u user
     * @return the id of the newly created user
     */
    User create(User u);

    /**
     * Update an existing user.
     *
     * @param u updated user with immutable id.
     * @return the id of the updated user
     */
    void update(User u);

    /**
     * Delete a user.
     *
     * @param u user
     * @return the id of the deleted user
     */
    void delete(User u);
}
