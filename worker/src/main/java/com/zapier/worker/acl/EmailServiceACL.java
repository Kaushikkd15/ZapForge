package com.zapier.worker.acl;

import com.zapier.worker.domain.action.ActionResult;
import com.zapier.worker.domain.action.SendEmailAction;

public interface EmailServiceACL {
    ActionResult sendEmail(SendEmailAction action);
}
