alter table forma_pagamento add updated_at timestamp null;
update forma_pagamento set updated_at = current_timestamp;
alter table forma_pagamento alter column updated_at set not null;