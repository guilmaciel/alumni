package com.alumni.host;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * Name: Guilherme Maciel.
 * Creation Date: 2022-03-11
 * Last Update: 2022-03-12
 *
 * JPARepository implementation to handle HostDataEntity queries.
 */

public interface HostDataRepository extends JpaRepository<HostDataEntity, String> {

}
