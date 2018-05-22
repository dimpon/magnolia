package com.dimpon.magnolia.repository;

import java.util.List;

public interface ServiceRepository {

    List<String> getPetsList(String name);
}
