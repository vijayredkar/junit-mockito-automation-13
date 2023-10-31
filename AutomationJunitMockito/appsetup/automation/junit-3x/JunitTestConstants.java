package PLCHLDR-AUTOMATION-PCKG ;

public class JunitTestConstants {
	
	public static String mockMvcJunitTemplateForGet =
	 "//--------------------------- MockMvc tests\r\n"
			+ "    //assert-verify results\r\n"
			+ "    result.andDo(print())                 \r\n"
			+ "    		.andExpect(status().is2xxSuccessful())\r\n"
			+ "             /*\r\n"
			+ "             .andExpect(status().is1xxInformational())\r\n"
			+ "             .andExpect(status().is2xxSuccessful())\r\n"
			+ "             .andExpect(status().is3xxRedirection())\r\n"
			+ "             .andExpect(status().is4xxClientError())\r\n"
			+ "             .andExpect(status().is5xxServerError())\r\n"
			+ "             .andExpect(status().isAccepted())\r\n"
			+ "             .andExpect(status().isBadGateway())\r\n"
			+ "             .andExpect(status().isCreated())\r\n"
			+ "             .andExpect(status().isForbidden())\r\n"
			+ "             .andExpect(status().isMethodNotAllowed())\r\n"
			+ "             .andExpect(status().isBadRequest())\r\n"
			+ "             .andExpect(status().isNoContent())\r\n"
			+ "             .andExpect(status().isNotFound())\r\n"
			+ "             .andExpect(status().isOk())\r\n"
			+ "             .andExpect(status().isRequestTimeout())\r\n"
			+ "             .andExpect(status().is\r\n"
			+ "             .andExpect(status().isRequestTimeout())\r\n"
			+ "             .andExpect(status().isTooManyRequests())\r\n"
			+ "             .andExpect(status().isUnauthorized()))\r\n"
			+ "             */                 \r\n"
			+ "             /*\r\n"
			+ "             .andExpect(content().string(containsString(\"\"title\":\"testTitle\"\")))\r\n"
			+ "             */\r\n"
			+ "             .andExpect(jsonPath(\"$.PLCHOLDER-FIELD-RAW\", is(dtoFactory.getPLCHOLDER-DTO-NAME().getPLCHOLDER-FIELD-NAME())))"
			+ "            ;\r\n"
			+ "\r\n"
			+ "//--only for Controller tests\r\n"
			+ "";
	
	
	
	public static String mockMvcJunitTemplateForNonGet =
			 "//--------------------------- MockMvc tests\r\n"
					+ "    //assert-verify results\r\n"
					+ "    result.andDo(print())                 \r\n"
					+ "    		.andExpect(status().is2xxSuccessful())\r\n"
					+ "             /*\r\n"
					+ "             .andExpect(status().is1xxInformational())\r\n"
					+ "             .andExpect(status().is2xxSuccessful())\r\n"
					+ "             .andExpect(status().is3xxRedirection())\r\n"
					+ "             .andExpect(status().is4xxClientError())\r\n"
					+ "             .andExpect(status().is5xxServerError())\r\n"
					+ "             .andExpect(status().isAccepted())\r\n"
					+ "             .andExpect(status().isBadGateway())\r\n"
					+ "             .andExpect(status().isCreated())\r\n"
					+ "             .andExpect(status().isForbidden())\r\n"
					+ "             .andExpect(status().isMethodNotAllowed())\r\n"
					+ "             .andExpect(status().isBadRequest())\r\n"
					+ "             .andExpect(status().isNoContent())\r\n"
					+ "             .andExpect(status().isNotFound())\r\n"
					+ "             .andExpect(status().isOk())\r\n"
					+ "             .andExpect(status().isRequestTimeout())\r\n"
					+ "             .andExpect(status().is\r\n"
					+ "             .andExpect(status().isRequestTimeout())\r\n"
					+ "             .andExpect(status().isTooManyRequests())\r\n"
					+ "             .andExpect(status().isUnauthorized()))\r\n"
					+ "             */                 \r\n"
					+ "            ;\r\n"
					+ "\r\n"
					+ "//--only for Controller tests\r\n"
					+ "";


}
