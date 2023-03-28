import io.vertx.core.http.HttpMethod
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.ext.web.handler.CorsHandler
import io.vertx.ext.web.handler.StaticHandler
import io.vertx.ext.web.Router

def router = Router.router(vertx)

router.route().handler(CorsHandler.create("*").allowedMethods([HttpMethod.GET,HttpMethod.POST].toSet()))
router.route().handler(BodyHandler.create().setBodyLimit(8000))

router.route().pathRegex("^(?!/backend)/.*").handler(StaticHandler.create())

router.route("/backend/dbTypes").handler({ routingContext ->
    DBTypes.getAllTypes(vertx, { dbTypes ->
        RESTUtils.writeJSONResponse(routingContext, dbTypes)
    })
})

router.post("/backend/createSchema").handler({ routingContext ->
    (new SchemaDef(vertx)).processCreateRequest(routingContext.getBodyAsJson(), { response ->
        RESTUtils.writeJSONResponse(routingContext, response)
    })
})

router.post("/backend/executeQuery").handler({ routingContext ->
    (new Query(vertx, routingContext.getBodyAsJson())).execute({ response ->
        if (response.error) {
            response.sets = [[ERRORMESSAGE:response.error]]
        }
        RESTUtils.writeJSONResponse(routingContext, response)
    })
})

router.route(HttpMethod.GET, "/backend/loadContent/:dbtypeid/:shortcode").handler({ routingContext ->
    Integer db_type_id
    String short_code

    try {
        db_type_id = Integer.parseInt(routingContext.request().getParam("dbtypeid"))
        short_code = routingContext.request().getParam("shortcode")
    } catch (NumberFormatException e) {
        RESTUtils.write404Response(routingContext)
        return
    }
    (new SchemaDef(vertx, db_type_id, short_code)).getBasicDetails({ response ->
        if (!response) {
            RESTUtils.write404Response(routingContext)
        } else {
            RESTUtils.writeJSONResponse(routingContext, response)
        }
    })
})

router.route(HttpMethod.GET, "/backend/loadContent/:dbtypeid/:shortcode/:queryid").handler({ routingContext ->
    Integer db_type_id
    String short_code
    Integer query_id

    try {
        db_type_id = Integer.parseInt(routingContext.request().getParam("dbtypeid"))
        short_code = routingContext.request().getParam("shortcode")
        query_id = Integer.parseInt(routingContext.request().getParam("queryid"))
    } catch (NumberFormatException e) {
        RESTUtils.write404Response(routingContext)
        return
    }

    def query = new Query(vertx, db_type_id, short_code, query_id)

    query.getBasicDetails({ response ->
        if (!response) {
            RESTUtils.write404Response(routingContext)
        } else {
            query.execute({ resultSets ->
                if (resultSets.error) {
                    response.sets = [[ERRORMESSAGE:resultSets.error]]
                } else {
                    response.sets = resultSets.sets
                }
                RESTUtils.writeJSONResponse(routingContext, response)
            })
        }
    })

})



def server = vertx.createHttpServer()
server.requestHandler(router.&accept).listen(8081)
