This project has end points to do the CRUD operation on a enroll

#GET
endpoint: /health/data/enroll

This endpoint will get the list of enroll currently present in the database

#POST
endpoint: /health/data/enroll

This end point will create a new enroll

endpoint: /health/data/enroll/{id}/dependent

This end point will create a new dependent for a already present enroll

#PUT
endpoint: /health/data/enroll

This end point will update an already present enroll

endpoint: /health/data/enroll/{id}/dependent

This end point will update an dependent for an already present enroll

#Delete
endpoint: /health/data/enroll/{id}

This end point will delete an enroll and need to provide the enroll id


endpoint: /health/data/enroll/{enrollId}/dependent/{dependentId}

This end point will delete an depeendent and need to provide the enroll id and dependent Id

